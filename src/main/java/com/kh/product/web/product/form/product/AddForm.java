package com.kh.product.web.product.form.product;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddForm {

  @NotBlank
  private String pname;
  @NotNull
  @Range(min=1, max=100)
  private Integer quantity;
  @NotNull
  @Min(1)
  private Long price;

}
