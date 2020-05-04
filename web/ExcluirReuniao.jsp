<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Excluir Reunião</title>
    </head>
    <body>
    <h1>Exclusão de Reunião</h1> 

<form action="ExcluirReuniao" method="Post">

    <fieldset style="width: 720px">
 <legend>Dados da reunião: </legend>
 <table class="table table-hover" border="1">
  <tr>
   <td>
    <label for="nome">Nome: </label>
   </td>
   <td align="left">
       <input type="text" name="nomeAssoc" value="${param.nomeAssoc}" size="40" maxlength="80">
   </td>    
  <tr>
   <td>
    <label for="numero">Numero: </label>
   </td>
   <td align="left">
       <input type="text" name="numeroAssoc" value="${param.numeroAssoc}" size="50" maxlength="30"> 
   </td>
  </tr>
 </table>
<input type="submit" value = "Excluir">
<input type="reset" value="Limpar">
</fieldset>
</form>
    </body>
</html>
