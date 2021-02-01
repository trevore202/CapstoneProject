<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
  
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">

<meta charset="ISO-8859-1">
<title>Checkout</title>
</head>
<body>

<nav class="navbar navbar-expand-sm navbar-dark bg-dark mb-3">
        <div class="container">
            <a class="navbar-brand" href="#">Music Store</a>
            <ul class="navbar-nav">
            
            <c:if test="${name != null}">
                <li class="nav-item">
                    <a class='nav-link'>Hi, ${name}!</a>
                </li>
            </c:if>
                <li class="nav-item">
                    <a class="nav-link" href="/gohome">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/listproducts">Products</a>
                </li>
                
                <c:if test="${name != null}">
                	<c:if test="${role.equals('customer')}">
                		<li class="nav-item">
                    		<a class="nav-link" href="/checkout">Cart</a>
                		</li>
                	</c:if>
                	<c:if test="${role.equals('admin')}">
                		<li class="nav-item dropdown">
                    		<a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown" aria-expanded="false">Admin Actions</a>
                    		<div class="dropdown-menu">
                        		<a href="/editusers" class="dropdown-item">Edit Users</a>
                        		<a href="/listproducts" class="dropdown-item">Edit Products</a>
                   			</div>
                		</li>
                	</c:if>
                </c:if>
                <c:if test="${name==null}">
                <li class="nav-item dropdown">
                    <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown" aria-expanded="false">Login or Register</a>
                    <div class="dropdown-menu">
                        <a href="/login" class="dropdown-item">Login</a>
                        <a href="/register" class="dropdown-item">Register</a>
                    </div>
                </li>
                </c:if>
                
                <c:if test="${name != null}">
                <li class="nav-item">
                	<a class="nav-link" href="/logout">Logout</a>
                </li>
                </c:if>
            </ul>
        </div>
    </nav>


<div class='container'>

<c:if test="${!role.equals('customer')}">
	<p>You must be logged in to see a cart!</p><br/>
	<a href='/login'>Login</a><br/>
	<a href='register'>Register</a><br/>
	<p><a href='/listproducts'>Click Here</a> to go back to product list</p>
</c:if>

<c:if test="${role.equals('customer')}">
${name}'s cart
<br/>
<c:if test="${cartItems.size() == 0 || cartItems == null}">
	<br/><p style='color:red'>Your cart is empty!</p>
</c:if>

<c:if test="${cartItems.size() != 0 && cartItems != null}">
<table class="table table-striped">
	<thead>
	<tr>
		<th>Name</th>
		<th>Image</th>
		<th>Product Price</th>
		<th>Quantity</th>
		<th>Action</th>
	</tr>
	</thead>

<c:forEach items="${cartItems}" var="product">
	<tr>
		<td>${product.productName}</td>
		<td><img src='${product.productImage}' height='100' width='100'></td>
		<td>${product.productPrice}</td>
		
		
		<c:forEach items ="${cartQuantities}" var='qty' varStatus='loop'>
			<c:if test="${cartItems.indexOf(product)==loop.index}">
			<td>${qty}</td>
			</c:if>
		</c:forEach>
		
		<td><a type='button' class='btn btn-outline-danger' href='/removeitem?id=${product.id}'>Remove</a></td>

	</tr>
</c:forEach>

<tr>
	<td>Total: ${TotalPrice}


	<td><a type="button" class="btn btn-success"
			href="/finalizeorder">Checkout</a></td>
	<td></td>
	<td></td>
	<td></td>
	
</tr>

</table>
</c:if>

<br/> <a href='/listproducts'>Back to Shopping</a>

</c:if>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>

</body>
</html>