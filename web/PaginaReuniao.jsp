<%@page import="associacao.Reuniao"%>
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
        <title>Gestão Reunião</title>
    </head>
    <body>
        <h1>Gestão De Reunião</h1> 
<form action="PesquisarReuniao" method="get">   
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
<legend>Pesquisar Reunião: </legend>
 <table cellspacing="10">
  <tr>
   <td>
    <label for="nome">Inicio: </label>
   </td>
   <td align="left">
    <input type="text" name="inicio" size="50" maxlength="80">
   </td>
  </tr>
  <tr> 
  <td>
    <label for="numero">Fim: </label>
   </td>
   <td align="left">
    <input type="text" name="fim" size="10" maxlength="6"> 
   </td>
  </tr>
 </table>
<c:set var="num" value="${param.numeroAssoc}"></c:set>
<input type="submit" value = "Pesquisar" style="margin-left: 250px;">
</fieldset>                

<fieldset style="width: 720px;">
 <legend>Reuniões Recuperadas: </legend>
<table class="table table-hover" border="1">
<tr>
    <td style="width: 420px;">DATA</td>
    <td style="width: 100px;">TIPO</td>
    <td style="width: 100px;">INICIO</td>
    <td style="width: 100x;">FIM</td>
</tr>
<jsp:useBean id="dao" class="dao.DAOReuniao"/>
<c:forEach var="reuniao" items="${dao.recuperarReunioes(num)}" varStatus="id">
    <tr bgcolor="#${id.count % 2 == 0 ? 'aa6e88' : 'eea2af' }" >
      <td>${reuniao.data}</td>
      <td>${reuniao.pauta}</td>
      <td>${param.inicio}</td>
      <td>${param.fim}</td>
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
<input type="submit" value = "Reuniões">
</fieldset> 

<fieldset style="width: 720px;">
<legend>Dados Da Reunião: </legend>
 <table class="table table-hover" border="1">
<%
    String msg = (String)request.getAttribute("msg");
try {
    Reuniao reuniao = (Reuniao)request.getAttribute("reuniao");%>
    <%=msg%>
    <tr><td>Data:</td>
        <td><input type="text" value="<%if(reuniao != null){%><%=reuniao.getData()%><%}%>" name="numero" size="85" maxlength="6"/></td>
    </tr>
    <tr><td>Pauta:</td>
        <td><input type="text" value="<%if(reuniao != null){%><%=reuniao.getPauta() %><%}%>" name="nome" size="85" maxlength="80"/></td>
    </tr>
    <%} catch (Exception e) {
    }%>
</table>   
</fieldset>
<fieldset style="width: 720px;">
<legend>Dados Da Reunião: </legend>
 <table class="table table-hover" border="1">
     <tr>
    <td style="width: 420px;">NUMERO</td>
    <td style="width: 100px;">NOME</td>
    <td style="width: 100px;">NÍVEL</td>
    <td style="width: 100x;">PARTICIPOU?</td>
</tr>
<%
try {
    ArrayList<Associado> associados = (ArrayList<Associado>)request.getAttribute("associados");
    for (Associado as : associados) {%>
    <td><input type="text" value="<%if(as != null){%><%=as.getNumero()%><%}%>" name="numero" size="10" maxlength="6"/></td>
    <td><input type="text" value="<%if(as != null){%><%=as.getNome()%><%}%>" name="numero" size="50" maxlength="6"/></td>
    <td><input type="text" value="<%if(as != null){%><%=as.getNivel()%><%}%>" name="numero" size="10" maxlength="6"/></td>
    <td><input type="text" value="(sim)(não)" name="numero" size="10" maxlength="6"/></td>
      <%  }
    } catch (Exception e) {
    }%>
</table>   
</fieldset>
<br />
</form>
</body>
</html>
