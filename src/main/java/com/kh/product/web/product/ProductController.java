package com.kh.product.web.product;

import com.kh.product.domain.Product;
import com.kh.product.domain.product.ProductSVC;
import com.kh.product.web.product.form.product.AddForm;
import com.kh.product.web.product.form.product.EditForm;
import com.kh.product.web.product.form.product.ItemForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductSVC productSVC;

  // 등록 화면 Get
  @GetMapping("/add")
  public String addForm(Model model){
    model.addAttribute("form", new AddForm());

    return "product/addForm";
  }

  // 등록 처리 Post
  @PostMapping("/add")
  public String add (
          @Valid @ModelAttribute("form") AddForm addForm,
          BindingResult bindingResult,
          RedirectAttributes redirectAttributes) {

    // 검증
    if(bindingResult.hasErrors()){
      return "product/addForm";
    }
    // 상품명 중복 검사
    Boolean isExist = productSVC.dupChkOfProductName(addForm.getPname());
    if(isExist) {
      bindingResult.rejectValue("pname","dup.pname","동일한 상품명이 존재합니다.");
      return "product/addForm";
    }

    if(addForm.getPrice() != null && addForm.getQuantity() != null){
      Long totalPrice = addForm.getPrice() * addForm.getQuantity();
      if(totalPrice >= 10000000) {
        bindingResult.reject("total", new Object[]{10000000, totalPrice}, "수량과 판매금액의 총액이 10000000(천만)원 이상입니다");

        return "product/addForm";
      }
    }

    // 상품 등록
    Product product = new Product();
    product.setPname(addForm.getPname());
    product.setQuantity(addForm.getQuantity());
    product.setPrice(addForm.getPrice());

    Product insertedProduct = productSVC.insert(product);

    Long id = insertedProduct.getPid();
    redirectAttributes.addAttribute("id", id);

    return "redirect:/products/{id}";
  }

  // 조회
  @GetMapping("/{id}")
  public String findById(@PathVariable("id") Long id, Model model) {

    Product findedProduct = productSVC.findById(id);

    ItemForm itemForm = new ItemForm();
    itemForm.setPid(findedProduct.getPid());
    itemForm.setPname(findedProduct.getPname());
    itemForm.setQuantity(findedProduct.getQuantity());
    itemForm.setPrice(findedProduct.getPrice());

    model.addAttribute("form", itemForm);

    return "product/itemForm";
  }

  // 수정 화면 Get
  @GetMapping("/{id}/edit")
  public String editForm(@PathVariable("id") Long id, Model model){
    Product findedProduct = productSVC.findById(id);

    EditForm editForm = new EditForm();
    editForm.setPid(findedProduct.getPid());
    editForm.setPname(findedProduct.getPname());
    editForm.setQuantity(findedProduct.getQuantity());
    editForm.setPrice(findedProduct.getPrice());

    model.addAttribute("form", editForm);

    return "product/editForm";
  }

  // 수정 처리 Post
  @PostMapping("/{id}/edit")
  public String edit(
        @PathVariable("id") Long id,
        @Valid @ModelAttribute("form") EditForm editForm,
        BindingResult bindingResult  ) {

    //검증
    if(bindingResult.hasErrors()) {
      return "product/editForm";
    }

    if(editForm.getPrice() != null && editForm.getQuantity() != null){
      Long totalPrice = editForm.getPrice() * editForm.getQuantity();
      if(totalPrice >= 10000000) {
        bindingResult.reject("total", new Object[]{10000000, totalPrice}, "수량과 판매금액의 총액이 10000000(천만)원 이상입니다");

        return "product/editForm";
      }
    }

    Product product = new Product();
    product.setPname(editForm.getPname());
    product.setQuantity(editForm.getQuantity());
    product.setPrice(editForm.getPrice());

    int updateRow = productSVC.update(id, product);

    if(updateRow == 0) {
      return "product/editForm";
    }
    return "redirect:/products/{id}"; //회원 상세화면
  }

  // 삭제 처리
  @GetMapping("/{id}/delete")
  public String delete (@PathVariable("id") Long id) {
    int deleteRow = productSVC.delete(id);

    if(deleteRow == 0){
      return "redirect:/products/"+id;
    }
    return "redirect:/products/list"; //삭제 후 목록 화면으로
  }

  // 상품 목록 화면
  @GetMapping("/list")
  public String list (Model model){
    List<Product> products = productSVC.list();
    List<ItemForm> itemlist = new ArrayList<>();

    products.stream().forEach(item -> {
      ItemForm itemForm = new ItemForm();
      BeanUtils.copyProperties(item, itemForm);

      itemlist.add(itemForm);
    });

    model.addAttribute("list", itemlist);

    return "product/list";
  }

}
