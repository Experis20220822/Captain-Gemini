package services

import java.security.MessageDigest


class MarvelService {

  MessageDigest.getInstance("MD5").digest((Instant.now().toEpochMilli+"apiKey"+"apiPassword").getBytes()).map("%02X".format(_)).mkString


}
