package services

import models.Hero
import org.scalatestplus.play.PlaySpec

class MemoryHeroServiceSpec extends PlaySpec {
  "memoryHeroService" must {
    "return a list heros after I create a hero" in {
      val memoryHeroService = new MemoryHeroService()
      val hero = Hero(10L, "BigR", "Heart is what wins battles", 1, 2,3,4)
      memoryHeroService.create(hero)
      memoryHeroService.findAll().size mustBe (1)
    }

  }
}