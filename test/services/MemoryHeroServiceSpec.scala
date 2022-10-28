package services

import models.Hero
import org.scalatestplus.play.PlaySpec

class MemoryHeroServiceSpec extends PlaySpec {
  "memoryHeroService" must {
    "return a list heros after I create a hero" in {
      val memoryHeroService = new MemoryHeroService()
      val hero = Hero(10L, "BigR", "Heart is what wins battles", "https://www.wallpaperup.com/uploads/wallpapers/2013/06/14/102891/0fb6df085d56d99fec30784ea5f4772a-700.jpg", 2,3,4,4)
      memoryHeroService.create(hero)
      memoryHeroService.findAll().size mustBe (1)
    }

    "be able to update a Hero from the service" in {
      val memoryStadiumService = new MemoryHeroService();
      val hero = Hero(10L, "BigR", "Heart is what wins battles", "https://www.wallpaperup.com/uploads/wallpapers/2013/06/14/102891/0fb6df085d56d99fec30784ea5f4772a-700.jpg", 2,3,4,4)
      memoryStadiumService.create(hero)
      memoryStadiumService.findAll().size mustBe (1)
      val maybeStadium = memoryStadiumService.findById(10L).get
      val updated = hero.copy(name = "Birmingham")
      println(updated)
     // memoryStadiumService.update(updated)
      val result = memoryStadiumService.findById(10L).get
      result mustBe Hero(10L, "BigR", "Heart is what wins battles", "https://www.wallpaperup.com/uploads/wallpapers/2013/06/14/102891/0fb6df085d56d99fec30784ea5f4772a-700.jpg", 2,3,4,4)
      memoryStadiumService.findAll().size mustBe (1)
    }

  }
}