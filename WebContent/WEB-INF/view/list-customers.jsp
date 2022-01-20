<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>

<head>
	<title>List Customers</title>
	
	<!-- reference our style sheet -->
	
	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/style.css"/>
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>CRM - Customer Relationship Manager</h2>
		</div>
	</div>
	
	<div id="container">
	
		<div id="content">
		
		<!-- put new button: Add Customer -->
		
		<input type="button" value="Add Customer" 
		       onclick="window.location.href='showFormForAdd'; return false;" 
		       class="add-button"
		/>
		
		<!--  add a search box -->
        <form:form action="search" method="GET">Search customer: <input type="text" name="theSearchName" />
        		<c:choose>
        			<c:when test="${empty theSearchName}">
        			</c:when>
        			<c:otherwise>
        				<input type="submit" value="Search" class="add-button" />
        			</c:otherwise>
        		</c:choose>
        </form:form>
		       
		<!-- add out html table here -->
		<table>
			<tr>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Email</th>
				<th>Action</th>
			</tr>
			
			<!-- loop over and print our customers -->
			<c:forEach var="customer" items="${customers}">
			
				<!-- construct an "update" link with customer id -->
				<c:url var="updateLink" value="/customer/showFormForUpdate">
					<c:param name="customerId" value="${customer.id}"/>
				</c:url>
				
				<!-- construct an "delete" link with customer id -->
				<c:url var="deleteLink" value="/customer/delete">
					<c:param name="customerId" value="${customer.id}"/>
				</c:url>
				
				<tr>
					<td>${customer.firstName}</td>
					<td>${customer.lastName}</td>
					<td>${customer.email}</td>
					<td>
						<a href="${updateLink}">Update</a>
						|
						<a href="${deleteLink}" 
						   onClick="return confirm('Are you sure you want to delete ${customer.firstName}');">Delete</a>
					</td>
				</tr>
			</c:forEach>
			
		</table>
		
		<c:if test="${empty customers}">
			<p>No customers found.</p>
			<p>
				<a href="${pageContext.request.contextPath}/customer/list">Refresh list</a>
			</p>
		</c:if>
		
		</div>
		
	</div>

</body>

</html>