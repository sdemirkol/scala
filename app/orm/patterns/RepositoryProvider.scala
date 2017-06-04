package orm.patterns

import orm.entities.BaseTable
import slick.lifted.CanBeQueryCondition
import scala.concurrent.Future
import slick.lifted.Rep
import slick.driver.MySQLDriver.api._
import scala.reflect.{ ClassTag, classTag }
import orm.entities.BaseEntity
import slick.driver.MySQLDriver
import java.sql.Driver
import slick.driver.JdbcProfile
import play.api.db.slick._
import orm.entities._
import org.joda.time.DateTime
import play.api.Play
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.Await
//import types.DynamicSortBySupport._
import slick.ast.Ordering.Direction
import slick.ast.Ordering.Direction

class DnxGenericRepository[E <: BaseEntity: ClassTag, T <: BaseTable[E]](dbName: String, clazz: TableQuery[T]) {
  val clazzTable: TableQuery[T] = clazz
  lazy val clazzEntity = classTag[E].runtimeClass
  val query: TableQuery[T] = clazz
  val db = DatabaseConfigProvider.get[JdbcProfile](dbName)(Play.current).db 


  def getAll: Seq[E] = {
    Await.result(db.run(query.result), Duration.Inf)
  }

  def getById(id: Long): Option[E] = {
    Await.result(db.run(query.filter(_.ID === id).result.headOption), Duration.Inf)
  }

  def filter[C <: Rep[_]](expr: T => C)(implicit wt: CanBeQueryCondition[C]): Seq[E] = {
    Await.result(db.run(query.filter(expr).result), Duration.Inf)
  }

  /*def filter[C <: Rep[_]](expr: T => C, sortBy: Seq[(String, Direction)])(implicit wt: CanBeQueryCondition[C]): Seq[E] = {
    Await.result(db.run(query.filter(expr).dynamicSortBy(sortBy).result), Duration.Inf)
  }
  
  def sortBy(sortBy: Seq[(String, Direction)]): Seq[E] = {
    Await.result(db.run(query.dynamicSortBy(sortBy).result), Duration.Inf)
  }*/

  def count[C <: Rep[_]](expr: T => C)(implicit wt: CanBeQueryCondition[C]): Int = {
    val action = query.filter(expr).length.result
    Await.result(db.run(action), Duration.Inf)
  }

  def count: Int = {
    val action = query.length.result
    Await.result(db.run(action), Duration.Inf)
  }

  def any[C <: Rep[_]](expr: T => C)(implicit wt: CanBeQueryCondition[C]): Boolean = {
    val action = query.filter(expr).result.headOption.map(auth => auth.isDefined)

    Await.result(db.run(action), Duration.Inf)
  }

  def save(row: E) = {
    val action = (query returning query.map(_.ID)) += row
    Await.result(db.run(action), Duration.Inf)
  }  

  def updateById(id: Long, row: E): Int = {
    Await.result(db.run(query.filter(_.ID === id).update(row)), Duration.Inf)
  }

  def deleteById(id: Long): Int = {
    Await.result(db.run(query.filter(_.ID === id).delete), Duration.Inf)
  }
}

object RepositoryProvider {
    private val crudString: String = "crud_database"
    val Employee = new DnxGenericRepository[EmployeeET, EmployeeTable](crudString, TableQuery[EmployeeTable])
    
}