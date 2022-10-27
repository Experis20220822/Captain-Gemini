/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package services

import play.api.libs.ws._

import java.security.MessageDigest
import java.time.Instant
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration.DurationInt


case class MarvelService @Inject() (ws: WSClient, executionContext: ExecutionContext) {

  // the private and public key from Marvel developer portal
  val pvtkey = "2aa4681a7a399c503018492b81d8c30e537998f5"
  val pubkey = "1698a5027fb04eabbffc7f5c5714d4af"

  // gets a query param "ts" with the current time in milliseconds
  val ts = Instant.now().toEpochMilli

  // the Marvel API URL
  val url = "http://gateway.marvel.com/v1/public/characters"

  // query param for authentication md5 hash of ts+pubkey+pvtkey
  var keyHash = MessageDigest.getInstance("MD5").digest((ts+pubkey+pvtkey).getBytes()).map("%02X".format(_)).mkString

  // the GET request with the query params
  val request: WSRequest = ws
    .url(url)
    .addQueryStringParameters(s"$ts" -> "ts", s"$keyHash" -> "hash")
    .withRequestTimeout(10000.millis)

  // the result is stored in WSResponse
  val futureResponse: Future[WSResponse] = request.get()

  println(futureResponse)


}