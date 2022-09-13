package com.kh.product.domain.product;

import com.kh.product.domain.Product;

import java.util.List;

public interface ProductDAO {

  /**
   * 상품 등록
   * @param product 상품정보
   * @return 등록 건수
   */
  int insert(Product product);

  /**
   * 상품 조회 by 상품 아이디
   * @param pid 상품 아이디
   * @return 상품 정보
   */
  Product findById(Long pid);

  /**
   * 상품 수정
   * @param pid 상품 아이디
   * @param product 수정할 상품
   * @return 수정 건수
   */
  int update(Long pid, Product product);

  /**
   * 상품 삭제
   * @param pid 삭제할 아이디
   * @return 삭제 건수
   */
  int delete(Long pid);

  /**
   * 신규 상품아이디 생성
   * @return 상품 아이디
   */
  Long generatepid();

  /**
   * 상품 목록
   * @return 상품 전체
   */
  List<Product> list();

  /**
   * 상품명 중복 체크
   * @param pname 상품 이름
   * @return 존재하면 true
   */
  Boolean dupChkOfProductName(String pname);
}
