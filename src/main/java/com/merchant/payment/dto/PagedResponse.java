// src/main/java/com/merchant/payment/dto/response/PagedResponse.java
package com.merchant.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Paginated response wrapper")
public class PagedResponse<T> {
    
    @Schema(description = "List of items in current page")
    private List<T> content;
    
    @Schema(description = "Current page number (0-based)", example = "0")
    private int page;
    
    @Schema(description = "Number of items per page", example = "10")
    private int size;
    
    @Schema(description = "Total number of items", example = "100")
    private long totalElements;
    
    @Schema(description = "Total number of pages", example = "10")
    private int totalPages;
    
    @Schema(description = "Is this the first page?", example = "true")
    private boolean first;
    
    @Schema(description = "Is this the last page?", example = "false")
    private boolean last;
    
    @Schema(description = "Number of elements in current page", example = "10")
    private int numberOfElements;
    
    public static <T> PagedResponse<T> of(List<T> content, int page, int size, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / size);
        
        return PagedResponse.<T>builder()
            .content(content)
            .page(page)
            .size(size)
            .totalElements(totalElements)
            .totalPages(totalPages)
            .first(page == 0)
            .last(page >= totalPages - 1)
            .numberOfElements(content.size())
            .build();
    }
}