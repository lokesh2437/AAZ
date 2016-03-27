<%@page import="com.retailsols.ticket.TicketServer"%>
<%

boolean flag=false;

String req_type=request.getParameter("type");

if(req_type.equals("login")){

String emp_id=request.getParameter("empid");
String emp_pass=request.getParameter("pass");
flag=new TicketServer().getEmpStatus(emp_id, emp_pass);
}
else if(req_type.equals("validate")){
String ticket=request.getParameter("ticket_id");
flag=new TicketServer().getTicketStatus(ticket);
}
out.println(flag);


%>