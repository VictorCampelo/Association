<%@page import="taxas.Pagamento"%>
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
        <title>Gestão Pagamento</title>
    </head>
    <body>
        <h1>Gestão De Pagamentos</h1> 
<form action="PesquisarPagamento" method="get">   


<fieldset style="width: 720px;">
<legend>Identificação da Associado: </legend>
<table class="table table-hover" border="1">
  <tr>
   <td>
    Nome: 
   </td>
   <td align="left">
       <input type="text" name="nomeAssociado" value="${param.nomeAssociado}" size="50" maxlength="80">
  </tr>
  <tr>
   <td>
    Numero: 
   </td>
   <td align="left">
        <input type="text" name="numeroAssociado" value="${param.numeroAssociado}" size="50" maxlength="80">
   </td>
  </tr>
  <tr>
   <td>
    Numero Associação: 
   </td>
   <td align="left">
        <input type="text" name="numeroAssociacao" value="${param.numeroAssociacao}" size="50" maxlength="80">
   </td>
  </tr>
 </table>
</fieldset>                


<fieldset style="width: 720px;">
<legend>Pesquisar Por Pagamentos: </legend>
 <table class="table table-hover" border="1">
  <tr>
   <td>
    <label for="nome">Taxa: </label>
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
<c:set var="num" value="${param.numeroAssociado}"></c:set>
<input type="submit" value = "Pesquisar" style="margin-left: 250px;">
</fieldset>                

<fieldset style="width: 720px;">
 <legend>Associados Recuperados: </legend>
<table class="table table-hover" border="1">
<tr>
    <td style="width: 100px;">DATA</td>
    <td style="width: 220px;">TAXA</td>
    <td style="width: 100px;">VIGENCIA</td>
    <td style="width: 100x;">VALOR ANO</td>
    <td style="width: 100x;">VALOR PAGO</td>
</tr>
<jsp:useBean id="dao" class="dao.DAOPagamento"/>
<c:forEach var="pag" items="${dao.recuperarPagamento(num, param.numeroAssociacao)}" varStatus="id">
    <tr bgcolor="#${id.count % 2 == 0 ? 'aa6e88' : 'eea2af' }" >
      <td>${pag.data2}</td>
      <td>${pag.nome}</td>
      <td>${pag.vigencia}</td>
      <td>${pag.valorAno}</td>
      <td>${pag.valor}</td>
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
    Pagamento taxa = (Pagamento)request.getAttribute("pag");%>
    <%=msg%>
    <tr><td>Data:</td>
        <td><input type="text" value="<%if(taxa != null){%><%=taxa.getData2()%><%}%>" name="nome" size="85" maxlength="80"/></td>
    </tr>
    <tr><td>Vigência:</td>
        <td><input type="text" value="<%if(taxa != null){%><%=taxa.getVigencia()%><%}%>" name="numero" size="85" maxlength="6"/></td>
    </tr>
    <tr><td>Taxa:</td>
        <td><input type="text" value="<%if(taxa != null){%><%=taxa.getNome()%><%}%>" name="numero" size="85" maxlength="6"/></td>
    </tr>
    <tr><td>Parcelas:</td>
        <td><input type="text" value="<%if(taxa != null){%><%=taxa.getParcelas()%><%}%>"name="cidade" size="85" maxlength="80"/></td>
    </tr> 
    <tr><td>Valor Anual:</td>
        <td><input type="text" value="<%if(taxa != null){%><%=taxa.getValorAno()%><%}%>" name="endereco" size="85" maxlength="120"/></td>
    </tr>
    <tr><td>Valor Pago:</td>
        <td><input type="text" value="<%if(taxa != null){%><%=taxa.getValor()%><%}%>" name="endereco" size="85" maxlength="120"/></td>
    </tr>
    <%} catch (Exception e) {
    }%>
</table>   
</fieldset>
<br />
</form>
</body>
</html>

