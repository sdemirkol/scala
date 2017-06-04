package controllers.admin

import play.api.mvc._
import play.api.i18n._
import play.twirl.api.Html
import orm.patterns.RepositoryProvider
import scala.concurrent._
import scala.concurrent.duration.Duration
import slick.driver.MySQLDriver.api._
import play.api.db.slick._
import slick.driver.JdbcProfile
import types.DnxCurrentUser

class Layout extends Controller{
  
}
object LayoutRender extends Controller{
  def header(request:RequestHeader):Html={
    views.html.admin.shared.partials.header.render()
  }
  def sideBar(request:RequestHeader):Html={
    views.html.admin.shared.partials.sideBar.render()
  }
}