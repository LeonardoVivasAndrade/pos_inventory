<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat"%>
 
<%
   Date dNow = new Date();
   SimpleDateFormat ft = new SimpleDateFormat ("yyyy");
   String currentDate = ft.format(dNow);
%>

<footer class="main-footer">
    <strong>Copyright &copy <%=currentDate%>. Todos los derechos reservados. versión 1.0012
        
        -Desarrollado por: <a href="https://www.linkedin.com/in/LeonardoVivasAndrade/" target="_blank">Leonardo Vivas</a>
    </strong>
    
</footer>
