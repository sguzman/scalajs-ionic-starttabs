package com.olivergg.ristorantewatcher.controller

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow
import scala.scalajs.js
import scala.scalajs.js.JSON
import scala.scalajs.js.annotation.JSExportAll
import scala.util.Failure
import scala.util.Success
import scala.util.Try
import com.greencatsoft.angularjs.Controller
import com.greencatsoft.angularjs.core.HttpService
import com.greencatsoft.angularjs.core.Scope
import com.greencatsoft.angularjs.inject
import com.olivergg.ristorantewatcher.dto.IPAddress
import prickle._
import com.olivergg.ristorantewatcher.helper.HttpHelpers

object AccountController extends Controller {

  override val name = "AccountCtrl"

  @inject
  implicit var http: HttpService = _

  override def initialize(scope: ScopeType) {
    println("init " + name)
    val ipAddressFut: Future[Try[IPAddress]] = HttpHelpers.getJsonAndUnpickle[IPAddress]("http://ip.jsontest.com/")
    ipAddressFut.onSuccess {
      case Success(ip) =>
        println(s"response from server IP = $ip"); scope.ip = ip
      case Failure(err) => println(s"something went wrong = $err"); scope.ip = IPAddress("ERROR")
    }

  }

  trait ScopeType extends Scope {
    var ip: IPAddress
  }
}

