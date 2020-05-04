<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro de uma nova taxa</title>
    </head>
    <body>
    <h1>Cadastra de taxa</h1> <br /> 
  <%String msg = (String)request.getAttribute("msg");%>
        <h2><%=msg%></h2>
        
<form action="CadastrarTaxa" method="Post">

<fieldset>
 <legend>Dados Cadastrais: </legend>
 <table class="table table-hover" border="1">
  <tr>
   <td>
    <label for="nome">Numero Associação: </label>
   </td>
   <td align="left">
       <input type="text" name="numeroAssoc" value="${param.numeroAssoc}" size="10" maxlength="6">
   </td>
  </tr>
  <tr>
   <td>
    <label for="nome">Nome Taxa: </label>
   </td>
   <td align="left">
       <input type="text" name="nomeTaxa" value="" size="40" maxlength="30">
   </td>
  </tr>
  <tr>
   <td>
    <label for="numero">Ano: </label>
   </td>
   <td align="left">
       <input type="text" name="ano" value="" size="7" maxlength="4">
   </td>
  </tr>
  <tr>
   <td>
    <label for="numero">Valor Anual: </label>
   </td>
   <td align="left">
       <input type="text" name="valorano" value="" size="10" maxlength="7"> 
   </td>
  </tr>
   <tr>
   <td>
    <label for="nivel">Parcelas: </label>
   </td>
   <td align="left">
       <input type="text" name="parcelas" value="" size="7" maxlength="2"> 
   </td>
  </tr>
  
  <tr>
   <td>
    <label for="numero">Vigencia: </label>
   </td>
   <td align="left">
       <input type="text" name="vigencia" value="" size="7" maxlength="4"> 
   </td>
  </tr>
 
 </table>
</fieldset>

<input type="submit" value = "Cadastrar">
<input type="reset" value="Limpar">
</form>
    </body>
</html>
