package org.bachert.imageorganizer.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class CrawlFileResultDTO {

    private long imageCount;
}
