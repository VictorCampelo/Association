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
        <title>Gestão Associados</title>
    </head>
    <body>
        <h1>Gestão De Associados</h1> 
<form action="PesquisarAssociado" method="get">   
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
    <label for="numero">Numero: </label>
   </td>
   <td align="left">
    <input type="text" name="numero" size="10" maxlength="6"> 
   </td>
  </tr>
  <tr> 
  <td>
    <label for="nivel">Nível: </label>
   </td>
   <td align="left">
    <input type="text" name="nivel" size="10" maxlength="30"> 
   </td>
  </tr>
 </table>
<c:set var="num" value="${param.numeroAssoc}"></c:set>
<input type="submit" value = "Pesquisar" style="margin-left: 250px;">
</fieldset>                
</form>
<fieldset style="width: 720px;">
 <legend>Associados Recuperados: </legend>
<table class="table table-hover" border="1">
<tr>
    <td style="width: 420px;">NOME</td>
    <td style="width: 100px;">NUMERO</td>
    <td style="width: 100px;">TELEFONE</td>
    <td style="width: 100x;">NÍVEL</td>
</tr>
<jsp:useBean id="dao" class="dao.DAOAssociado"/>
<c:forEach var="assoc" items="${dao.recuperaAssociados(num)}" varStatus="id">
    <tr bgcolor="#${id.count % 2 == 0 ? 'aa6e88' : 'eea2af' }" >
      <td>${assoc.nome}</td>
      <td>${assoc.numero}</td>
      <td>${assoc.telefone}</td>
      <td>${assoc.nivel}</td>
    </tr>
</c:forEach>
</table>
<br>
<input type="button" value = "Novo" onclick="parent.location.href='CadastroAssociado.jsp?numeroAssoc=${param.numeroAssoc}&nomeAssoc=${param.nomeAssoc}'" style="margin-left: 80px;">
<input type="submit" value = "Alterar">
<input type="submit" value = "Visualizar">
<input type="submit" value = "Excluir">
<input type="button" value = "Pagamentos" onclick="parent.location.href='PaginaPagamento.jsp?numeroAssociado=${param.numero}&nomeAssociado=${param.nome}&numeroAssociacao=${param.numeroAssoc}'">
<input type="submit" value = "Reuniões">
</fieldset> 
<form action="CadastroAssociado" method="post">
<fieldset style="width: 720px;">
<legend>Dados Do Associado: </legend>
 <table class="table table-hover" border="1">
<%
    String msg = (String)request.getAttribute("msg");
try {
    Associado as = (Associado)request.getAttribute("as");%>
    <%=msg%>
    <tr><td>Número Associacao:</td>
        <td><input type="text" value="${param.numeroAssoc}" name="numeroAssoc" size="85" maxlength="6"/></td>
    </tr>
    <tr><td>Número:</td>
        <td><input type="text" value="<%if(as != null){%>${as.numero}<%}%>" name="numero" size="85" maxlength="6"/></td>
    </tr>
    <tr><td>Nome:</td>
        <td><input type="text" value="<%if(as != null){%><%=as.getNome() %><%}%>" name="nome" size="85" maxlength="80"/></td>
    </tr>
    <tr><td>Nível:</td>
        <td><input type="text" value="<%if(as != null){%><%=as.getNivel() %><%}%>" name="nivel" size="85" maxlength="120"/></td>
    </tr>
    <tr><td>CPF:</td>
        <td><input type="text" value="<%if(as != null){%><%=as.getCpf()%><%}%>"name="cpf" size="85" maxlength="80"/></td>
    </tr>
    <tr><td>Nascimento:</td>
        <td><input type="text" value="<%if(as != null){%><%=as.getNascimento()%><%}%>" name="nascimento" size="85" maxlength="80"/></td>
    </tr>    
    <tr><td>Endereço:</td>
        <td><input type="text" value="<%if(as != null){%><%=as.getEndereco()%><%}%>" name="endereco" size="85" maxlength="80"/></td>
    </tr>
    <tr><td>Cidade:</td>
        <td><input type="text" value="<%if(as != null){%><%=as.getCidade()%><%}%>" name="cidade" size="85" maxlength="80"/></td>
    </tr>
    <tr><td>Estado:</td>
        <td><input type="text" value="<%if(as != null){%><%=as.getEstado()%><%}%>" name="estado" size="85" maxlength="80"/></td>
    </tr>
    <tr><td>Email:</td>
        <td><input type="text" value="<%if(as != null){%><%=as.getEmail()%><%}%>" name="email" size="85" maxlength="80"/></td>
    </tr>
    <tr><td>Telefone:</td>
        <td><input type="text" value="<%if(as != null){%><%=as.getTelefone()%><%}%>" name="telefone" size="85" maxlength="80"/></td>
    </tr>
    <tr><td>WhatsApp:</td>
        <td><input type="text" value="<%if(as != null){%><%=as.getWhatsapp()%><%}%>" name="whatsapp" size="85" maxlength="80"/></td>
    </tr>
    <%} catch (Exception e) {
    }%>
</table>   
<input type="submit" value = "Salvar"  style="margin-left: 250px;">
</fieldset>
</form>
<br />
</body>
</html>
