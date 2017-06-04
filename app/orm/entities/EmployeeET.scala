package orm.entities

import java.sql._
import play.api.data.Form
import play.api.data.Forms._
import slick.driver.MySQLDriver.api._
import play.api.db.slick._
import slick.driver.JdbcProfile
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future
import org.joda.time.DateTime
import slick.lifted.ProvenShape
import scala.reflect.ClassTag

case class EmployeeET(override val ID:Long, val Name:String, val Surname:String,  override val InsertDate: DateTime, override val UpdateDate: DateTime)
extends BaseEntity

case class EmployeeCrudFormData(ID: Option[Long], Name: String, Surname: String)

object EmployeeForm {
  var crud = Form(mapping(
    "ID" -> optional(longNumber),
    "Name" -> text,
    "Surname" -> text)(EmployeeCrudFormData.apply)(EmployeeCrudFormData.unapply))
}

class EmployeeTable(tag:Tag) extends BaseTable[EmployeeET](tag,"Employee"){  
  val Name:Rep[String]=column[String]("Name")
  val Surname:Rep[String]=column[String]("Surname")
  
  def * = (ID, Name, Surname, InsertDate, UpdateDate) <> (EmployeeET.tupled, EmployeeET.unapply)

  val sortMap = Map("ID" -> (this.ID))
}