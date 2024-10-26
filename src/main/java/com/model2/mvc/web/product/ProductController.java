package com.model2.mvc.web.product;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@Controller
@RequestMapping("/product/*")
public class ProductController {

    ///Field
    @Autowired
    @Qualifier("productServiceImpl")
    private ProductService productService;
    
    //setter Method 구현 않음
    
    public ProductController(){
        System.out.println(this.getClass());
    }
    
    @Value("#{commonProperties['pageUnit']}")
    int pageUnit;
    
    @Value("#{commonProperties['pageSize']}")
    int pageSize;
    
    @Value("#{commonProperties['path']}")
    String path;
    
    @Autowired
    ServletContext servletContext;
    
    //==> addProduct GET
    @RequestMapping( value = "addProduct", method=RequestMethod.GET)
    public String addProduct() throws Exception {
        System.out.println("/product/addProduct : GET");
        return "redirect:/product/addProductView.jsp";
    }
    
    //==> addProduct POST
    @RequestMapping( value = "addProduct", method = RequestMethod.POST)
    public String addProduct(
    		@RequestParam("file") MultipartFile file,  // 파일 업로드를 위한 MultipartFile 추가
        @ModelAttribute("product") Product product, Model model) throws Exception {
        
        
    	
        System.out.println("/product/addProduct : POST");
        
        	
        
//        // 파일이 비어 있지 않으면 처리
//        if (!file.isEmpty()) {
//            // 업로드 경로 설정
//            String uploadDir = "C:\\WorkSpace\\11.Model2MVCShop10.23\\src\\main\\webapp\\images\\uploadFiles\\";
//            
//            // 디렉토리 생성
//            File dir = new File(uploadDir);
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
//            
//            // 파일 저장
//            String filePath = uploadDir + file.getOriginalFilename();
//            file.transferTo(new File(filePath));
//            
//            // Product 객체에 파일 경로 저장 (이미지 필드 추가 필요)
//            product.setFileName(file.getOriginalFilename());
//        }
//        
//        // 비즈니스 로직 처리
//        productService.addProduct(product);
//        
//        return "forward:/product/addProduct.jsp";
//    }
  
        
        String fileName = file.getOriginalFilename();
        String uploadPath = servletContext.getRealPath(path);
         String saveFile = uploadPath + fileName;
         
         System.out.println("asd"+fileName);
         try {
            if( !file.isEmpty() ) {
	            System.out.println("in");
	             file.transferTo(new File(saveFile));
	             product.setFileName(fileName);
            }else {
            	product.setFileName("");
            
            }
             productService.addProduct(product);
             
         } catch (IOException e) {
             e.printStackTrace();
             // 예외 처리 로직 추가 (예: 에러 메시지 전달)
         }
        
        //productService.updateProduct(product);
        
        //return "redirect:/product/updateProduct?prodNo="+product.getProdNo();
        return "forward:/product/addProduct.jsp";
     }
        
    //==> getProduct
    @RequestMapping( value = "getProduct", method = RequestMethod.GET)
    public String getProduct( @RequestParam("prodNo") int prodNo , Model model ) throws Exception {
        System.out.println("/product/getProduct : GET");
        Product product = productService.getProduct(prodNo);
        model.addAttribute("product", product);
        return "forward:/product/getProduct.jsp";
    }
    
    //==> updateProductView
    @RequestMapping( value ="updateProductView", method = RequestMethod.GET)
    public String updateProductView( @RequestParam("prodNo") int prodNo , Model model ) throws Exception {
        System.out.println("/product/updateProductView : GET");
        Product product = productService.getProduct(prodNo);
        model.addAttribute("product", product);
        return "forward:/product/updateProductView.jsp";
    }
    
    //==> updateProduct POST
    @RequestMapping( value = "updateProduct" , method = RequestMethod.POST)
    public String updateProduct( @ModelAttribute("product") Product product , Model model , HttpSession session) throws Exception {
        System.out.println("/product/updateProduct : POST");
        productService.updateProduct(product);
        model.addAttribute("product", product);
        return "redirect:/product/getProduct?prodNo=" + String.valueOf(product.getProdNo());
    }
    
    //==> listProduct
    @RequestMapping( value = "listProduct")
    public String listProduct( @ModelAttribute("search") Search search ,@RequestParam("menu") String menu, Model model , HttpServletRequest request) throws Exception {
        System.out.println("/product/listProduct : GET / POST");
        
        if (search.getCurrentPage() == 0) {
            search.setCurrentPage(1);
        }
        search.setPageSize(pageSize);
        
        // 비즈니스 로직 수행
        Map<String, Object> map = productService.getProductList(search);
        
        Page resultPage = new Page(search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
        System.out.println(resultPage);
        
        // Model과 View 연결
        model.addAttribute("list", map.get("list"));
        model.addAttribute("resultPage", resultPage);
        model.addAttribute("search", search);
        model.addAttribute("menu", menu);
        
        return "forward:/product/listProduct.jsp";
    }
}
