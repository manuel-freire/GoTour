Feature: browser automation 2
#mvn exec:java
Background:
  # chromium bajo linux; 
  # si usas google-chrome, puedes quitar toda la parte de executable
  * configure driver = { type: 'chrome', showDriverLog: true }
  * url baseUrl
  * def util = Java.type('karate.KarateTests')

Scenario: Registrarse, crear un tour y editar la direccion de correo nueva
    Given driver 'http://localhost:8080/index'
  * input('#username', 'vicky')
  * input('#password', 'aa')
  * submit().click("[type=submit]")
  * click("a[id=logo_box]")
  * input('#fechaInicial', '10-10-2021'+Key.RIGHT+'11:40')
  * input('#ciudad', 'AAAAAAAA')
  * driver.screenshot()