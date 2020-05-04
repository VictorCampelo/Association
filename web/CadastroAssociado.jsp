<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro Associação</title>
    </head>
    <body>
    <h1>Cadastro De Associado</h1> 
  <h2> Preencha o formulário corretamente: </h2><br /> 
  <%String msg = (String)request.getAttribute("msg");%>
        <h2><%=msg%></h2>
        
<form action="CadastroAssociado" method="Post">
<!-- DADOS CADASTRAIS-->
<fieldset>
 <legend>Dados Cadastrais: </legend>
 <table cellspacing="10">
  <tr>
   <td>
    <label for="nome">Numero Associação: </label>
   </td>
   <td align="left">
       <input type="text" name="numeroAssoc" value="${param.numeroAssoc}" size="85" maxlength="80">
   </td>
  </tr>
  <tr>
   <td>
    <label for="nome">Nome Associação: </label>
   </td>
   <td align="left">
       <input type="text" name="nomeAssoc" value="${param.nomeAssoc}" size="85" maxlength="80">
   </td>
  </tr>
  <tr>
   <td>
    <label for="nome">Nome: </label>
   </td>
   <td align="left">
       <input type="text" name="nome" value="" size="85" maxlength="80">
   </td>
  </tr>
  <tr>
   <td>
    <label for="numero">Numero: </label>
   </td>
   <td align="left">
       <input type="text" name="numero" value="" size="10" maxlength="6"> 
   </td>
  </tr>
   <tr>
   <td>
    <label for="nivel">Nível: </label>
   </td>
   <td align="left">
       <input type="text" name="nivel" value="" size="10" maxlength="6"> 
   </td>
  </tr>
  <tr>
   <td>
    <label for="nascimento">Nascimetno: </label>
   </td>
   <td align="left">
       <input type="text" name="nascimento" value="" size="10" maxlength="6"> 
   </td>
  </tr>
  <tr>
   <td>
    <label for="cpf">CPF: </label>
   </td>
   <td align="left">
       <input type="text" name="cpf" value="" size="10" maxlength="6"> 
   </td>
  </tr>
 </table>
</fieldset>

<br />
<!-- ENDEREÇO -->
<fieldset>
 <legend>Dados de Endereço</legend>
 <table cellspacing="10">

  <tr>
   <td>
    <label for="endereco">Endereço:</label>
   </td>
   <td align="left">
    <input type="text" name="endereco" size="120" maxlength="120">
   </td>
  </tr>
  <tr>
   <td>
    <label for="cidade">Cidade: </label>
   </td>
   <td align="left">
    <input type="text" name="cidade" size="85" maxlength="80">
   </td>
  </tr>
  <tr>
   <td>
    <label for="estado">Estado:</label>
   </td>
   <td align="left">
    <select name="estado"> 
    <option value="Acre">Acre</option> 
    <option value="Alagoas">Alagoas</option> 
    <option value="Amazonas">Amazonas</option> 
    <option value="Amapá">Amapá</option> 
    <option value="Bahia">Bahia</option> 
    <option value="Ceará">Ceará</option> 
    <option value="Distrito Federal">Distrito Federal</option> 
    <option value="Espírito Santo">Espírito Santo</option> 
    <option value="Goiás">Goiás</option> 
    <option value="Maranhão">Maranhão</option> 
    <option value="Mato Grosso">Mato Grosso</option> 
    <option value="Mato Grosso do Sul">Mato Grosso do Sul</option> 
    <option value="Minas Gerais">Minas Gerais</option> 
    <option value="Pará">Pará</option> 
    <option value="Paraíba">Paraíba</option> 
    <option value="Paraná">Paraná</option> 
    <option value="Pernambuco">Pernambuco</option> 
    <option value="Piauí">Piauí</option> 
    <option value="Rio de Janeiro">Rio de Janeiro</option> 
    <option value="Rio Grande do Norte">Rio Grande do Norte</option> 
    <option value="Rondônia">Rondônia</option> 
    <option value="Rio Grande do Sul">Rio Grande do Sul</option> 
    <option value="Roraima">Roraima</option> 
    <option value="Santa Catarina">Santa Catarina</option> 
    <option value="Sergipe">Sergipe</option> 
    <option value="São Paulo">São Paulo</option> 
    <option value="Tocantins">Tocantins</option> 
   </select>
   </td>
  </tr>
 </table>
</fieldset>
<br />

<fieldset>
 <legend>Dados De Contato: </legend>
 <table cellspacing="10">
  <tr>
   <td>
    <label for="telefone">Telefone: </label>
   </td>
   <td align="left">
       <input type="text" name="telefone" value="" size="85" maxlength="80">
   </td>
  </tr>
   <tr>
   <td>
    <label for="whatsapp">Whatsapp: </label>
   </td>
   <td align="left">
       <input type="text" name="whatsapp" value="" size="85" maxlength="80">
   </td>
  </tr>
  <tr>
   <td>
    <label for="email">Email: </label>
   </td>
   <td align="left">
       <input type="text" name="email" value="" size="10" maxlength="6"> 
   </td>
  </tr>
 </table>
</fieldset>
<!-- SALVAR -->
<br />
<input type="submit" value = "Cadastrar">
<input type="reset" value="Limpar">
</form>
    </body>
</html>
