<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="dao.DAOAssociacao"%>
<%@page import="exceptions.ElementoInexistente"%>
<%@page import="associacao.Associacao"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastrar Associação</title>
    </head>
    <body>
        <h1>Gestão Associação</h1> 
        <%String msg = (String)request.getAttribute("msg");%>
        <h2><%=msg%></h2>
<form action="PesquisarAssociacao" method="get">
<fieldset style="width: 600px;">
<legend>Pesquisar Por Associação: </legend>
 <table cellspacing="10">
  <tr>
   <td>
    <label for="nome">Nome: </label>
   </td>
   <td align="left">
    <input type="text" name="nomeAssoc" size="85" maxlength="80">
  <tr>
   <td>
    <label for="numero">Numero: </label>
   </td>
   <td align="left">
    <input type="text" name="numeroAssoc" size="10" maxlength="6"> 
   </td>
 </table>
<input type="submit" value = "Pesquisar" style="margin-left: 250px;">
<input type="reset" value="Limpar" style="width: 78px;padding-left: 0px;padding-right: 0px;">
</fieldset>       
</form>
        
<fieldset style="width: 720px;">
 <legend>Associações recuperadas: </legend>
<table class="table table-hover" border="1">
<tr>
    <td style="width: 130px;">NUMERO</td>
    <td style="width: 600px;">NOME</td>
</tr>
    
<jsp:useBean id="dao" class="dao.DAOAssociacao"/>

    <c:forEach var="assoc" items="${dao.recuperarAssociacao()}" varStatus="id">
    <tr bgcolor="#${id.count % 2 == 0 ? 'aa6e88' : 'eea2af' }" >
      <td>${assoc.numero}</td>
      <td>${assoc.nome}</td>
    </tr>
</c:forEach>
</table>
<br>
<input type="button" value = "Novo" onclick="parent.location.href='CadastroAssociacaoJSP.jsp'" style="margin-left: 80px;">
<input type="submit" value = "Alterar">
<input type="submit" value = "Visualizar">
<input type="button" value = "Excluir" onclick="parent.location.href='ExcluirAssociacao.jsp?numeroAssoc=${param.numeroAssoc}&nomeAssoc=${param.nomeAssoc}'">
<input type="button" value = "Associados" onclick="parent.location.href='PaginaAssociado.jsp?numeroAssoc=${param.numeroAssoc}&nomeAssoc=${param.nomeAssoc}'">
<input type="button" value = "Taxas" onclick="parent.location.href='PaginaTaxa.jsp?numeroAssoc=${param.numeroAssoc}&nomeAssoc=${param.nomeAssoc}'">
<input type="button" value = "Reuniões" onclick="parent.location.href='PaginaReuniao.jsp?numeroAssoc=${param.numeroAssoc}&nomeAssoc=${param.nomeAssoc}'">
</fieldset> 
<form action="CadastroAssociacao" method="post">
<fieldset style="width: 720px;">
<legend>Dados Cadastrais: </legend>
 <table class="table table-hover" border="1"> 
<%try {
    Associacao as = (Associacao)request.getAttribute("as");%>
    <tr><td>Número:</td>
        <td><input type="text" value="<%if(as != null){%>${as.numero}<%}%>" name="numero" size="85" maxlength="6"/></td>
    </tr>
    <tr><td>Nome:</td>
        <td><input type="text" value="<%if(as != null){%><%=as.getNome() %><%}%>" name="nome" size="85" maxlength="80"/></td>
    </tr>
    <tr><td>Endereço:</td>
        <td><input type="text" value="<%if(as != null){%><%=as.getEndereco() %><%}%>" name="endereco" size="85" maxlength="120"/></td>
    </tr>
    <tr><td>Cidade:</td>
        <td><input type="text" value="<%if(as != null){%><%=as.getCidade() %><%}%>"name="cidade" size="85" maxlength="80"/></td>
    </tr>
    <tr><td>Estado:</td>
        <td><input type="text" value="<%if(as != null){%><%=as.getEstado() %><%}%>" name="estado" size="85" maxlength="80"/></td>
    </tr>    
    <%        
    } catch (Exception e) {%>
    <%}%>
</table> 
<br>
<input type="submit" value = "Salvar" style="margin-left: 250px;">
</fieldset>
</form>
<br />
</body>
</html>
