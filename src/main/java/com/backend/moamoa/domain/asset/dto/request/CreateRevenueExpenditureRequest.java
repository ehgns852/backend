package com.backend.moamoa.domain.asset.dto.request;

import com.backend.moamoa.domain.asset.entity.RevenueExpenditureType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRevenueExpenditureRequest {

    @ApiModelProperty(value = "수익, 지출 타입", example = "REVENUE", required = true)
    private RevenueExpenditureType revenueExpenditureType;

    @ApiModelProperty(value = "해당 년월일", example = "2022-04-19", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;

    @ApiModelProperty(value = "카테고리 이름", example = "월급", required = true)
    private String categoryName;

    @ApiModelProperty(value = "결제 수단", example = "신용 카드")
    private String paymentMethod;

    @ApiModelProperty(value = "금액", example = "6500", required = true)
    private int cost;

    @ApiModelProperty(value = "내용", example = "신전 떡볶이")
    private String content;

}
