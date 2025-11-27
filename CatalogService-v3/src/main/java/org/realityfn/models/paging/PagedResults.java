package org.realityfn.models.paging;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({ "elements", "paging" })
public class PagedResults<T> {
    @JsonProperty("elements")
    private List<T> data;

    private Paging paging;

    public PagedResults(List<T> data, int start, int count, long total) {
        this.data = data;
        this.paging = new Paging(start, count, total);
    }

    public List<T> getData() {
        return data;
    }
    public void setData(List<T> data) {
        this.data = data;
    }
    public Paging getPaging() {
        return paging;
    }
    public void setPaging(Paging paging) {
        this.paging = paging;
    }
}
