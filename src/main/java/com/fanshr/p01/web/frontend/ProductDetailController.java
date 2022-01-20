package com.fanshr.p01.web.frontend;

import com.fanshr.p01.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : LiuYJ
 * @version : v1.0
 * @date : Created at 2021/11/23 10:15
 * @date : Modified at 2021/11/23 10:15
 */
@Controller
@RequestMapping("/frontend/")
public class ProductDetailController {

    @Autowired
    private ProductService productService;
    // TODO: 待定 
    
}
