Feature: browser automation 2
#mvn exec:java
Background:
  # chromium bajo linux; 
  # si usas google-chrome, puedes quitar toda la parte de executable
  * configure driver = { type: 'chrome', showDriverLog: true }
  * url baseUrl
  * def util = Java.type('karate.KarateTests')

Scenario: Registrarse, crear un tour y editar la direccion de correo nueva
    Given driver 'http://localhost:8080/registro'
  * input('#nombre', 'Manolo')
  * input('#apellidos', 'Ejemplo')
  * input('#email', 'email@ejemplo1')
  * input('#password', 'patata')
  * input('#username', 'Aragorn')
  * input('#respuestaseguridad', 'Alpargato')
  * input('#telefono', '111222333')
  * submit().click("[type=submit]")
  * driver.screenshot()
  * match html('title') contains 'GoTour'
  * driver.screenshot()
  * click("a[id=perfil_ref]")
  * match html('title') contains 'Perfil'
  * click("a[id=crearTour]")
  * driver.screenshot()
  * input('#pais', 'España')
  * input('#ciudad', 'Madrid')
  * input('#lugar', 'Sol')
  * input('#titulo', 'Tour de Ejemplo')
  * input('#descripcion', 'Tour muy bonito que se hace para empezar a probar la funcionalidad de crearTours')
  * driver.screenshot()
  * input('#fechaIni', '10-10-2021')
  * input('#fechaFin', '10-11-2021')
  * input('#maxTuristas', '15')
  * input('#precio', '10')
  * driver.screenshot()
  * submit().click("[type=submit]")
  * match html('title') contains 'Tour'
  * driver.screenshot()
  * click("a[id=perfil_ref]")
  * driver.screenshot()
  * click("a[id=datosPrivados]")
  * match html('title') contains 'Datos Privados'
  * driver.screenshot()
  * click("a[id=editarPriv]")
  * driver.screenshot()
  * value('#email', '')
  * input('#email', 'emailbueno@gmail.com')
  * submit().click("[type=submit]")
  * driver.screenshot()


