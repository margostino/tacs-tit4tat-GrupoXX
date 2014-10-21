<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row row-offcanvas row-offcanvas-left">

  <div class="col-xs-12 col-sm-9">
    <h1 class="page-header">Items de mis amigos!</h1>
  </div>
</div>

<table class="table table-striped table-hover">
<thead>
<th>Codigo</th>
<th>Descripcion</th>
<th>Propietario</th>
<th>Acciones</th>
</thead>
<tbody>
<c:forEach var="item" items="${items}">
        <tr>
        <td>${item.id}</td>
        <td>${item.description}</td>
        <td>${item.owner.name}</td>
        <td><a href="#"><span onclick="createTrueque(this)" class="glyphicon glyphicon-cloud-upload" title="Envia una solicitud de trueque a tu amigo!!"></span></a></td>
        </tr>
      </c:forEach>
      </tbody>
</table>      

<script type="text/javascript">

function createTrueque(link){
  var id = $(link).closest("tr").find("td:eq(0)").text();
  var redirect = "notifications/create/" + id + ".htm";
  window.location.replace(redirect);
}

  
}

</script>