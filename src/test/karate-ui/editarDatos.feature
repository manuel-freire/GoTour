Feature: Editar datos de usuario
#mvn exec:java
Background:
  # chromium bajo linux; 
  # si usas google-chrome, puedes quitar toda la parte de executable
  * configure driver = { type: 'chrome', showDriverLog: true }
  * url baseUrl
  * def util = Java.type('karate.KarateTests')

Scenario: logear, elegir un tour e inscribirse al tour
    Given driver 'http://localhost:8080/login'
    #Iniciamos sesion
  * input('#username', 'SPACEMARINE')
  * input('#password', 'aa')
   #vamos a la pagina de inicio para acceder a un tour
  * submit().click("[type=submit]")
  * match html('title') contains 'Perfil'
  * click("a[id=editarPerfil]")
  * match html('title') contains 'Editar Datos'
  #Cambiamos el mail y añadimos un idioma
  * value('#email', '')
  * input('#email', 'emailbueno@gmail.com')
  * input('#idioma', 'Mandarín')
  * click("[id=add-languaje]")
  * driver.screenshot()
  * click("[id=Actualizar]")
  * driver.screenshot()
