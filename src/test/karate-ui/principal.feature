Feature: browser automation 1
#mvn exec:java
Background:
  # chromium bajo linux; 
  # si usas google-chrome, puedes quitar toda la parte de executable
  * configure driver = { type: 'chrome', executable: '/opt/google/chrome/google-chrome', showDriverLog: true }
  * url baseUrl
  * def util = Java.type('karate.KarateTests')

Scenario: logear, elegir un tour e inscribirse al tour
    Given driver 'http://localhost:8080/login'
  * input('#username', 'SPACEMARINE')
  * input('#password', 'aa')
  * submit().click("button[type=submit]")
  * match html('title') contains 'Perfil'
  * click("a[id=logo_box]")
  * match html('title') contains 'GoTour'
  * driver.screenshot()
  * click("a[id=tour_ref]")
  * match html('title') contains 'Tour'
  #* input('#turistas', "1")
  * submit().click("button[type=submit]")
  * delay(5000) 
  * match html('#libre') contains '1'
  #* driver.screenshot()
  #* click("[id=ver_perfil]")
  #* driver.screenshot()

