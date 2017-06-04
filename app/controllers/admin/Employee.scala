package controllers.admin

import play.api.mvc._
import play.api.i18n._
import play.twirl.api.Html
import orm.patterns.RepositoryProvider
import orm.entities._
import scala.concurrent._
import scala.concurrent.duration.Duration
import slick.driver.MySQLDriver.api._
import play.api.db.slick._
import slick.driver.JdbcProfile
import types.DnxCurrentUser
import java.util.concurrent.TimeoutException
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.data._
import play.api.data.Forms._
import org.joda.time._
import play.api.libs.json._

class Employee extends Controller {

  def save = Action { implicit request =>
    Ok(views.html.admin.employee.insert(EmployeeForm.crud)(request))
  }
  /*def save=Action{ implicit request =>
    Ok(views.html.main())
  }*/
  
  def insert() = Action.async { implicit request =>
    EmployeeForm.crud.bindFromRequest.fold(
      // if any error in submitted data
      errorForm => Future.successful(Ok(views.html.admin.employee.insert(errorForm))),
      data => {
        val entity = EmployeeET(0, data.Name,data.Surname,DateTime.now(),DateTime.now())
        val id = RepositoryProvider.Employee.save(entity)
      })
      Future(Ok(Json.toJson("kayıt başarılı")))
  }
  def update(id:Long)=Action{implicit request=>
    val entity = RepositoryProvider.Employee.filter(x => x.ID === id).headOption
    val anyData = Map("ID"-> 5,"Name" -> "ali", "Surname" -> "Gelme")
    if (!entity.isDefined)
      NotFound("Http Error : 404 - User Not Found")
    else {
      Future(entity.map(employee=>Ok(views.html.admin.employee.update(EmployeeForm.crud.bind(anyData)))))
    }
  }
  def updatePut()=Action.async{implicit request=>
    EmployeeForm.crud.bindFromRequest.fold(
      errorForm=>Future.successful(Ok(views.html.admin.employee.update(errorForm))),
      formData => {
        var entity = RepositoryProvider.Employee.filter(x => x.ID === formData.ID).headOption
        if (entity.isDefined) {
          val copy = entity.get.copy(Name = formData.Name, Surname = Some(formData.Surname.getOrElse("-")),
            UpdateDate = DateTime.now())
          val id = RepositoryProvider.Employee.updateById(copy.ID, copy)
          
        }
      })
      Future(Ok(Json.toJson("kayıt başarılı")))
  }
 /* def insert(employee: Employee)=Action{implicit request=>
    var entity=EmployeeET(0,employee.Name,employee.Surname,DateTime.now(),DateTime.now())
    RepositoryProvider.Employee.save(employee)
    try db.run(employees += employee)
    finally db.close
  def save: Action[AnyContent] = Action.async { implicit request =>
    EmployeeForm.crud.bindFromRequest.fold(
      formWithErrors => Future.successful(BadRequest(html.admin.employee.insert(formWithErrors))),
      formData=>{
        var entity=EmployeeET(0,formData.Name,Some(formData.Description.getOrElse("-")),
            DateTime.now(),DateTime.now())
        Some(formData.Description.getOrElse("-"))
        if(id!=0.toLong){
          
        }
      }
      
      formWithErrors => Future.successful(BadRequest(html.admin.employee.insert(formWithErrors))),
      formData => {
        var entity = EmployeeET(0, formData.Name, Some(formData.Description.getOrElse("-")),
          DateTime.now(), DateTime.now())
        val futureEmpInsert = RepositoryProvider.Employee.save(entity)
        futureEmpInsert.map { result => Home.flashing("success" -> "Employee %s has been created".format(formData.name)) }.recover {
          case ex: TimeoutException =>
            Logger.error("Problem found in employee save process")
            InternalServerError(ex.getMessage)
        }
      })
  }*/

  /*def save: Action[AnyContent] = Action.async { implicit request =>
    employeeForm.bindFromRequest.fold(
      formWithErrors => Future.successful(BadRequest(html.createForm(formWithErrors))),
      employee => {
        val futureEmpInsert = dao.insert(employee)
        futureEmpInsert.map { result => Home.flashing("success" -> "Employee %s has been created".format(employee.name)) }.recover {
          case ex: TimeoutException =>
            Logger.error("Problem found in employee save process")
            InternalServerError(ex.getMessage)
        }
      })
  }
  CategoryForm.crud.bindFromRequest.fold(
      errors => {
        result.Notification = Some(notification)
      },
      formData => {
        var entity = CategoryET(0, formData.Name, Some(formData.Description.getOrElse("-")),
          DateTime.now(), DateTime.now())
        val id = RepositoryProvider.Category.save(entity)
        if (id != 0.toLong) {
          notification.Status = "success"
          notification.Message = Messages("admin.category.crud.insert.success")
          result.Operation = "reset"
          result.IsSuccess = true
          result.Notification = Some(notification)
        }
      })
    messagesApi.setLang(Ok(DnxJResult.allowJson(result)), Lang(request.lang))*/
}