<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Excluir Pagamento</title>
    </head>
    <body>
    <h1>Exclusão de Pagamento</h1> 

<form action="ExcluirReuniao" method="Post">

    <fieldset style="width: 720px">
 <legend>Dados do pagamento: </legend>
 <table class="table table-hover" border="1">
  <tr>
   <td>
    <label for="numero">Numero Associacao: </label>
   </td>
   <td align="left">
       <input type="text" name="numeroAssociacao" value="${param.nomeAssoc}" size="20" maxlength="10">
   </td>    
  <tr>
   <td>
    <label for="numero">Numero Associado: </label>
   </td>
   <td align="left">
       <input type="text" name="numeroAssociado" value="${param.numeroAssoc}" size="20" maxlength="10"> 
   </td>
  </tr>
  
  <tr>
   <td>
    <label for="numero">Mes: </label>
   </td>
   <td align="left">
       <input type="text" name="mes" value="${param.numeroAssoc}" size="5" maxlength="2"> 
   </td>
  </tr>
  
  <tr>
   <td>
    <label for="nome">Nome da taxa: </label>
   </td>
   <td align="left">
       <input type="text" name="nomeTaxa" value="${param.nomeTaxa}" size="50" maxlength="30"> 
   </td>
  </tr>
  
  <tr>
   <td>
    <label for="numero">Vigência: </label>
   </td>
   <td align="left">
       <input type="text" name="vigencia" value="${param.vigencia}" size="10" maxlength="5"> 
   </td>
  </tr>
  
 </table>
<input type="submit" value = "Excluir">
<input type="reset" value="Limpar">
</fieldset>
</form>
    </body>
</html>
