package com.kh.product.domain.product;

import com.kh.product.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductSVCImpl implements ProductSVC{

  private final ProductDAO productDAO;

  /**
   * 상품 등록
   *
   * @param product 상품정보
   * @return 상품 아이디
   */
  @Override
  public Product insert(Product product) {

    Long generatepid = productDAO.generatepid();
    product.setPid(generatepid);
    productDAO.insert(product);

    return productDAO.findById(generatepid);
  }

  /**
   * 상품 조회 by 상품 아이디
   *
   * @param pid 상품 아이디
   * @return 상품 정보
   */
  @Override
  public Product findById(Long pid) {
    return productDAO.findById(pid);
  }

  /**
   * 상품 수정
   *
   * @param pid     상품 아이디
   * @param product 수정할 상품
   * @return 수정 건수
   */
  @Override
  public int update(Long pid, Product product) {
    int cnt = productDAO.update(pid, product);
    log.info("수정건수={}",cnt);
    return cnt;
  }

  /**
   * 상품 삭제
   *
   * @param pid 삭제할 아이디
   * @return 삭제 건수
   */
  @Override
  public int delete(Long pid) {
    int cnt = productDAO.delete(pid);
    log.info("삭제건수={}", cnt);
    return cnt;
  }

  /**
   * 상품 목록
   *
   * @return 상품 전체
   */
  @Override
  public List<Product> list() {
    return productDAO.list();
  }

  /**
   * 상품명 중복 체크
   *
   * @param pname 상품 이름
   * @return 존재하면 true
   */
  @Override
  public Boolean dupChkOfProductName(String pname) {
    return productDAO.dupChkOfProductName(pname);
  }
}
