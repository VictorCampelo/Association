<%@page import="taxas.Taxa"%>
<%@page import="associacao.Associado"%>
<%@page import="dao.DAOAssociado"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="dao.DAOAssociacao"%>
<%@page import="exceptions.ElementoInexistente"%>
<%@page import="associacao.Associacao"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns:h="http://xmlns.jcp.org/jsf/html" xmlns:f="http://xmlns.jcp.org/jsf/core">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gestão Taxas</title>
    </head>
    <body>
        <h1>Gestão De Taxas</h1> 
<form action="PesquisarTaxa" method="get">   
<fieldset style="width: 720px;">
<table    
<legend>Identificação da Associação: </legend>
  <tr>
   <td>
    Nome: 
   </td>
   <td align="left">
       <input type="text" name="nomeAssoc" value="${param.nomeAssoc}" size="50" maxlength="80">
  <tr>
   <td>
    Numero: 
   </td>
   <td align="left">
        <input type="text" name="numeroAssoc" value="${param.numeroAssoc}" size="50" maxlength="80">
   </td>
 </table>
</fieldset>                
<fieldset style="width: 720px;">
<legend>Pesquisar Associado: </legend>
 <table cellspacing="10">
  <tr>
   <td>
    <label for="nome">Nome: </label>
   </td>
   <td align="left">
    <input type="text" name="nome" size="50" maxlength="80">
   </td>
  </tr>
  <tr> 
  <td>
    <label for="numero">Vigencia: </label>
   </td>
   <td align="left">
    <input type="text" name="vigencia" size="10" maxlength="6"> 
   </td>
  </tr>
 </table>
<c:set var="num" value="${param.numeroAssoc}"></c:set>
<input type="submit" value = "Pesquisar" style="margin-left: 250px;">
</fieldset>                

<fieldset style="width: 720px;">
 <legend>Associados Recuperados: </legend>
<table class="table table-hover" border="1">
<tr>
    <td style="width: 420px;">NOME</td>
    <td style="width: 100px;">VIGÊNCIA</td>
    <td style="width: 100px;">VALOR ANO</td>
    <td style="width: 100x;">PARCELAS</td>
</tr>
<jsp:useBean id="dao" class="dao.DAOTaxa"/>
<c:forEach var="taxa" items="${dao.recuperarTaxas(num)}" varStatus="id">
    <tr bgcolor="#${id.count % 2 == 0 ? 'aa6e88' : 'eea2af' }" >
      <td>${taxa.nome}</td>
      <td>${taxa.vigencia}</td>
      <td>${taxa.valorAno}</td>
      <td>${taxa.parcelas}</td>
    </tr>
</c:forEach>
</table>
<br>
<input type="button" value = "Novo" onclick="parent.location.href='CadastroAssociacaoJSP.jsp'" style="margin-left: 80px;">
<input type="submit" value = "Alterar">
<input type="submit" value = "Visualizar">
<input type="submit" value = "Excluir">
<!--<input type="button" value = "Associados" onclick="parent.location.href='PaginaAssociado.jsp?numero=${param.numero}&nome=${param.nome}'">-->
<input type="submit" value = "Pagamentos">
<!--<input type="submit" value = "Reuniões">-->
</fieldset> 

<fieldset style="width: 720px;">
<legend>Dados Do Associado: </legend>
 <table class="table table-hover" border="1">
<%
    String msg = (String)request.getAttribute("msg");
try {
    Taxa taxa = (Taxa)request.getAttribute("taxa");%>
    <%=msg%>
    <tr><td>Nome:</td>
        <td><input type="text" value="<%if(taxa != null){%>${taxa.nome}<%}%>" name="nome" size="85" maxlength="80"/></td>
    </tr>
    <tr><td>Vigência:</td>
        <td><input type="text" value="<%if(taxa != null){%>${taxa.vigencia}<%}%>" name="numero" size="85" maxlength="6"/></td>
    </tr>
    <tr><td>Valor Anual:</td>
        <td><input type="text" value="<%if(taxa != null){%>${taxa.valorAno}<%}%>" name="endereco" size="85" maxlength="120"/></td>
    </tr>
    <tr><td>Parcelas:</td>
        <td><input type="text" value="<%if(taxa != null){%>${taxa.parcelas}<%}%>"name="cidade" size="85" maxlength="80"/></td>
    </tr>   
    <%} catch (Exception e) {
    }%>
</table>   
</fieldset>
<br />
</form>
</body>
</html>
