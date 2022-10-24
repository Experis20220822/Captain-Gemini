/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package viewmodels

abstract class WithCssClass(className: String) {
  override val toString: String = className
}
