<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page pageEncoding="EUC-KR"%>

<%-- 
// 	Product prod = (Product)request.getAttribute("product");
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="ko">
	
<head>
	<meta charset="EUC-KR">
	
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" >
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" >
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>
	
    <link href="/css/animate.min.css" rel="stylesheet">
   <link href="/css/bootstrap-dropdownhover.min.css" rel="stylesheet">
	
	<script src="/javascript/bootstrap-dropdownhover.min.js"></script>
	
	<style>
		body {
            padding-top : 50px;
        }
    </style>
    
    <script type="text/javascript">
	
    $(function() {// 수정 Event
		$( "button.btn.btn-primary" ).on("click" , function() {
			fncUpdateProduct();
		});
	});	
	
    $(function() {// 취소 Event
		$("a[href='#' ]").on("click" , function() {
			history.go(-1);
		});
	});	

    
    
function fncUpdateProduct() {
	var name=$("input[name='prodName']").val();
		var detail=$("input[name='prodDetail']").val();
		var manuDate=$("input[name='manuDate']").val();
		var price=$("input[name='price']").val();

    // 유효성 검증
    if (name == null || name.length < 1) {
        alert("상품명은 반드시 입력하여야 합니다.");
        return;
    }
    if (detail == null || detail.length < 1) {
        alert("상품상세정보는 반드시 입력하여야 합니다.");
        return;
    }
    if (manuDate == null || manuDate.length < 1) {
        alert("제조일자는 반드시 입력하셔야 합니다.");
        return;
    }
    if (price == null || price.length < 1) {
        alert("가격은 반드시 입력하셔야 합니다.");
        return;
    }



    // 폼 전송
    //document.detailForm.action = '/product/updateProduct'; 
    //document.detailForm.submit();
    $("form").attr("method" , "POST").attr("action", "/product/updateProduct" ).submit();
}
   
</script>

</head>

<body>

<jsp:include page="/layout/toolbar.jsp" />

<div class="container">

<div class="page-header text-center">
	       <h3 class=" text-info">상품수정</h3>
	       <h5 class="text-muted">상품을 <strong class="text-danger">최신정보로 관리</strong>해 주세요.</h5>
	    </div>

<form class="form-horizontal">
	<input type="hidden" class="form-control" id="prodNo" name="prodNo" value="${product.prodNo}" >
	
	
  <div class="form-group">
		    <label for="userName" class="col-sm-offset-1 col-sm-3 control-label">상품명</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" id="prodName" name="prodName" value="${product.prodName}" placeholder="변경상품명">
		    </div>
		  </div>

  <div class="form-group">
		    <label for="userName" class="col-sm-offset-1 col-sm-3 control-label">상품상세정보</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" id="prodDetail" name="prodDetail" value="${product.prodDetail}" placeholder="변경상품상세정보">
		    </div>
		  </div>
		  
		    <div class="form-group">
		    <label for="userName" class="col-sm-offset-1 col-sm-3 control-label">제조일자</label>
		    <div class="col-sm-4">
		      <input type="date" class="form-control" id="manuDate" name="manuDate" value="${product.manuDate}" placeholder="변경회원이름">
		    </div>
		  </div>
		  
		    <div class="form-group">
		    <label for="userName" class="col-sm-offset-1 col-sm-3 control-label">가격</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" id="price" name="price" onkeyup="chkNumber(this)" value="${product.price}" placeholder="변경가격">
		    </div>
		  </div>
		  
		    <div class="form-group">
		    <label for="userName" class="col-sm-offset-1 col-sm-3 control-label">상품이미지</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" id="fileName" name="fileName" value="${product.fileName}" placeholder="변경상품이미지">
		    </div>
		  </div>

	  <div class="form-group">
		    <div class="col-sm-offset-4  col-sm-4 text-center">
		      <button type="button" class="btn btn-primary"  >수 &nbsp;정</button>
			  <a class="btn btn-primary btn" href="#" role="button">취 &nbsp;소</a>
		    </div>
		  </div>
		</form>


	</div>
	
	</body>

	</html>

