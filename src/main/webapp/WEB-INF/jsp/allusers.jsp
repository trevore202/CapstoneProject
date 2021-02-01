<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
<meta charset="ISO-8859-1">
<title>Edit Users</title>
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

<c:if test="${!role.equals('admin')}">
	<p>You do not have access to view this page!</p><br/>
	<p><a href='/listproducts'>Click Here</a> to go back to product list</p>
</c:if>

<c:if test="${role.equals('admin')}">
<p style='color:blue'>${updatemessage}</p>

<br/>
<a type='button' class='btn btn-secondary' href='/gohome'>Back</a><br/><br/>

<table class="table table-striped">
			<thead>
				<tr>
					<th>Username</th>
					<th>Password</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Role</th>
					<th>Email</th>
					<th>Address</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${allUsers}" var="user">
					<tr>
						<td>${user.username}</td>
						<td>${user.password}</td>
						<td>${user.fname}</td>
						<td>${user.lname}</td>
						<td>${user.role}</td>
						<td>${user.email}</td>
						<td>${user.address}</td>
						<td><a type="button" class="btn btn-warning"
							href='/edituser?username=${user.username}'>Edit</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</c:if>

</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>

</body>
</html>