package org.realityfn.utils.database;

import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.realityfn.models.mongo.AccountFriendAndBlockedList;
import org.realityfn.models.mongo.BlockedModel;
import org.realityfn.models.mongo.FriendsModel;
import org.realityfn.utils.database.setup.BaseDAOImpl;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Tamely I hope you know I wrote this all inside of a plane (I'm locked in)

public class FriendsDAO extends BaseDAOImpl<AccountFriendAndBlockedList> {

    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("UTC"));

    public FriendsDAO() {
        super("accounts", AccountFriendAndBlockedList.class);
    }

    /**
     * Get all friend fields
     */
    public List<AccountFriendAndBlockedList> getAllFields() {
        try {
            return collection.find()
                    .map(doc -> {
                        try {
                            return mapper.readValue(doc.toJson(), AccountFriendAndBlockedList.class);
                        } catch (Exception e) {
                            throw new RuntimeException("Error mapping document", e);
                        }
                    })
                    .into(new ArrayList<>());
        } catch (Exception e) {
            throw new RuntimeException("Error finding fields", e);
        }
    }

    /**
     * Get summary with account id
     */
    public Optional<AccountFriendAndBlockedList> getFriendInfoWithAccountId(String accountId) {
        try {
            Document doc = collection.find(
                    Filters.eq("_id", accountId)).first();

            if (doc != null)
                return Optional.of(mapper.readValue(doc.toJson(), AccountFriendAndBlockedList.class));

            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Error finding FriendInfo for account: " + accountId, e);
        }
    }
    /**
     * Get current friends for account :fire:
     */
    public List<FriendsModel> getFriendsListWithAccountId(String accountId) {
        return getFriendInfoWithAccountId(accountId)
                .map(AccountFriendAndBlockedList::getCurrentFriends)
                .orElse(List.of());
    }

    /**
     * Get pending requests for account
     */
    public List<FriendsModel> getPendingFriendRequestsWithAccount(String accountId) {
        return getFriendInfoWithAccountId(accountId)
                .map(AccountFriendAndBlockedList::getPendingRequests)
                .orElse(List.of());
    }

    /**
     * Get BlockedUsers for account
     */
    public List<BlockedModel> getBlockedAccountsWithAccountId(String accountId) {
        return getFriendInfoWithAccountId(accountId)
                .map(AccountFriendAndBlockedList::getBlockedUsers)
                .orElse(List.of());
    }

    /**
     * Delete all friends for account
     */
    public void deleteAllFriends(String accountId) {
        getFriendInfoWithAccountId(accountId).ifPresent(account -> {
            account.setCurrentFriends(List.of());
            update(account);
        });
    }

    /**
     * Delete all blocked for account
     */
    public void deleteAllBlocked(String accountId) {
        getFriendInfoWithAccountId(accountId).ifPresent(account -> {
            account.setBlockedUsers(List.of());
            update(account);
        });
    }

    /**
     * Send a friend request from sendingAccountId to receivingAccountId
     * @param receivingAccountId is the account receiving the friend request
     * @param sendingAccountId is the account sending the friend request
     */

    public void sendRequestToUser(String sendingAccountId, String receivingAccountId) {
        try {
            AccountFriendAndBlockedList sendingAccount = findAccountOrThrow(sendingAccountId);
            AccountFriendAndBlockedList receivingAccount = findAccountOrThrow(receivingAccountId);

            sendingAccount.sendRequestToUser(receivingAccountId, DATE_TIME_FORMATTER.format(java.time.Instant.now()));
            receivingAccount.receiveRequestToUser(sendingAccountId, DATE_TIME_FORMATTER.format(java.time.Instant.now()));

            update(sendingAccount);
            update(receivingAccount);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while processing friend request for account: " + sendingAccountId, e);
        }
    }

    /**
     * Handle an incoming request from requestingFriendId to handlingAccountId.
     * @param handlingAccountId is the account who initiated the request
     * @param requestingFriendId is the account who sent the friend request
     * @param isAccepting if we're accepting/rejecting the incoming request
     */
    public void handleRequestFromUser(String handlingAccountId, String requestingFriendId, boolean isAccepting) {
        try {
            AccountFriendAndBlockedList handlingAccount = findAccountOrThrow(handlingAccountId);
            AccountFriendAndBlockedList requestingFriendAccount = findAccountOrThrow(requestingFriendId);

            handlingAccount.handlingIncomingRequest(requestingFriendId, isAccepting);
            requestingFriendAccount.handleOutgoingRequest(handlingAccountId, isAccepting);

            update(handlingAccount);
            update(requestingFriendAccount);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while processing friend request for account: " + handlingAccountId, e);
        }
    }


    public void removeFriendFromUser(String removingAccountId, String friendAccountId) {
        try {
            AccountFriendAndBlockedList removingAccount = findAccountOrThrow(removingAccountId);
            AccountFriendAndBlockedList friendAccount = findAccountOrThrow(friendAccountId);

            removingAccount.removeFriend(friendAccountId);
            friendAccount.removeFriend(removingAccountId);

            update(removingAccount);
            update(friendAccount);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while processing friend request for account: " + removingAccountId, e);
        }
    }

    /**
     * Blocks blockedAccountId
     * @param blockingAccountId is the account blocking
     * @param blockedAccountId is the account being blocked
     */
    public void handlingBlockingUser(String blockingAccountId, String blockedAccountId, boolean isBlocking) {
        try {
            AccountFriendAndBlockedList blockingAccount = findAccountOrThrow(blockingAccountId);

            if (isBlocking) blockingAccount.blockUser(blockedAccountId, DATE_TIME_FORMATTER.format(java.time.Instant.now()));
            else blockingAccount.unblockUser(blockedAccountId);
            update(blockingAccount);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while processing block request for account: " + blockedAccountId, e);
        }
    }

    /**
     * Update Friend Info
     */
    public AccountFriendAndBlockedList update(AccountFriendAndBlockedList accountInfo) {
        String accountId = accountInfo.getAccountId();
        return update(accountId, accountInfo);
    }


    /**
     * Save Friend Info
     */
    public AccountFriendAndBlockedList save(String accountId) {
        try {
            AccountFriendAndBlockedList accountFriendInfo = new AccountFriendAndBlockedList();
            accountFriendInfo.setAccountId(accountId);

            return save(accountFriendInfo);
        } catch (Exception e) {
            throw new RuntimeException("Error creating info", e);
        }
    }

    /**
     * Helper method to find an account by ID or throw a RuntimeException if not found.
     * @param accountId The ID of the account to find.
     * @return The found AccountFriendAndBlockedList object.
     */
    private AccountFriendAndBlockedList findAccountOrThrow(String accountId) {
        return getFriendInfoWithAccountId(accountId).orElseThrow(() -> new RuntimeException("Account not found: " + accountId));
    }
}
