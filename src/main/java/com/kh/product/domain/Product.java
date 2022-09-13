package com.kh.product.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
  private Long pid;           // 상품 ID
  private String pname;       // 상품명
  private Integer quantity;   // 수량 (100개 이하)
  private Long price;         // 가격 (수량*가격의 합이 10,000,000원 넘지 않아야 함)
}
