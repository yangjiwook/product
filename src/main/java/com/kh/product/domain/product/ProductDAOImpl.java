package com.kh.product.domain.product;

import com.kh.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor
public class ProductDAOImpl implements ProductDAO {

  private final JdbcTemplate jt;

  /**
   * 상품 등록
   *
   * @param product 상품정보
   * @return 등록 건수
   */
  @Override
  public int insert(Product product) {

    int result = 0;

    StringBuffer sql = new StringBuffer();
    sql.append("insert into product (pid, pname, quantity, price) " );
    sql.append("values (?, ?, ?, ?) ");

    result = jt.update(sql.toString(), product.getPid(), product.getPname(), product.getQuantity(), product.getPrice());


    return result;
  }

  /**
   * 상품 조회 by 상품 아이디
   *
   * @param pid 상품 아이디
   * @return 상품 정보
   */
  @Override
  public Product findById(Long pid) {
    StringBuffer sql = new StringBuffer();

    sql.append("select pid, pname, quantity, price ");
    sql.append("from product ");
    sql.append("where pid = ? ");

    Product findedProduct = null;

    try {
      //BeanPropertyRowMapper는 매핑되는 자바클래스에 디폴트생성자 필수!
      findedProduct = jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(Product.class), pid);
    } catch (DataAccessException e) {
      log.info("찾고자하는 상품이 없습니다!={}", pid);
    }

    return findedProduct;
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
    int result = 0;
    StringBuffer sql = new StringBuffer();

    sql.append(" update product ");
    sql.append("    set pname = ?, ");
    sql.append("        quantity = ?, ");
    sql.append("        price = ? ");
    sql.append("  where pid = ? ");

    result = jt.update(sql.toString(), product.getPname(), product.getQuantity(), product.getPrice(), pid);

    return result;
  }

  /**
   * 상품 삭제
   *
   * @param pid 삭제할 아이디
   * @return 삭제 건수
   */
  @Override
  public int delete(Long pid) {
    int result = 0;
    String sql = "delete from product where pid = ? ";

    result = jt.update(sql, pid);

    return result;
  }

  /**
   * 신규 상품아이디 생성
   *
   * @return 상품 아이디
   */
  @Override
  public Long generatepid() {
    String sql = "select product_pid_seq.nextval from dual ";
    Long pid = jt.queryForObject(sql, Long.class);

    return pid;
  }

  /**
   * 상품 목록
   *
   * @return 상품 전체
   */
  @Override
  public List<Product> list() {

    StringBuffer sql = new StringBuffer();
    sql.append("select pid, pname, quantity, price ");
    sql.append("  from product ");

    return jt.query(sql.toString(), new BeanPropertyRowMapper<>(Product.class));
  }

  /**
   * 상품명 중복 체크
   *
   * @param pname 상품 이름
   * @return 존재하면 true
   */
  @Override
  public Boolean dupChkOfProductName(String pname) {
    String sql = "select count(pname) from product where pname = ? ";
    //count가 반환되야하므로 Integer
    Integer rowCount = jt.queryForObject(sql, Integer.class, pname);
    return rowCount == 1 ? true : false;
  }
}
