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
  #* input('#nombre', 'Manolo')
  #* input('#apellidos', 'Ejemplo')
  #* input('#email', 'email@ejemplo1')
  #* input('#password', 'patata')
  #* input('#username', 'Aragorn')
  #* input('#respuestaseguridad', 'Alpargato')
  #* input('#telefono', '111222333')
  #En este punto hemos introducido todos los datos necesarios para crear un usuario
  #* driver.screenshot() 
  #* submit().click("[type=submit]")
  #* match html('title') contains 'GoTour'
  #* driver.screenshot()
  # Nos metemos en nuestro perfil
  #* click("a[id=perfil_ref]")
  #* match html('title') contains 'Perfil'
  #* click("a[id=crearTour]")
  #* driver.screenshot()
  # A continuacion creamos un tour
  #* input('#pais', 'España')
  #* input('#ciudad', 'Madrid')
  #* input('#lugar', 'Sol')
  #* input('#titulo', 'Tour de Ejemplo')
  #* input('#descripcion', 'Tour muy bonito que se hace para empezar a probar la funcionalidad de crearTours')
  #* driver.screenshot()
  #* input('#fechaInicial', '10'+Key.TAB +'10'+Key.TAB+'2021'+Key.TAB+'11'+Key.TAB+'40', 200)
  #* input('#fechaFinal', '10-11-2021 12:50')
  #* driver.screenshot()
  #* input('#maxTuristas', '15')
  #* input('#precio', '10')
  #* driver.screenshot()
  #* click("[type=submit]")
  #* match html('title') contains 'Tour'
  #* driver.screenshot()
  #* click("a[id=perfil_ref]")
  #* driver.screenshot()
  # Lo de editar no esta acabado aun
  #* click("a[id=datosPrivados]")
  #* match html('title') contains 'Datos Privados'
  #* driver.screenshot()
  #* click("a[id=editarPriv]")
  #* driver.screenshot()
  #* value('#email', '')
  #* input('#email', 'emailbueno@gmail.com')
  #* submit().click("[type=submit]")
  #* driver.screenshot()


