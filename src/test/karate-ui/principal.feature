Feature: browser automation 1
#mvn exec:java
Background:
  # chromium bajo linux; 
  # si usas google-chrome, puedes quitar toda la parte de executable
  * configure driver = { type: 'chrome', showDriverLog: true }
  * url baseUrl
  * def util = Java.type('karate.KarateTests')

Scenario: logear, elegir un tour e inscribirse al tour
    Given driver 'http://localhost:8080/login'
  * input('#username', 'vicky')
  * input('#password', 'aa')
  * submit().click("[type=submit]")
  * match html('title') contains 'Perfil'
  * click("a[id=logo_box]")
  * match html('title') contains 'GoTour'
  * driver.screenshot()
  * click("a[id=tour_ref]")
  * match html('title') contains 'Tour'
  #* input('#turistas', "1")
  * submit().click("[type=submit]")
  * match html('title') contains 'Pago por el tour'
  * driver.screenshot()
  #* click("[id=ver_perfil]")
  #* driver.screenshot()

