/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package viewmodels

abstract class WithName(name: String) {
  override val toString: String = name
}
