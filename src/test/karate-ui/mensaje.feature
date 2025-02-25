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
    #Iniciamos sesion
  * input('#username', 'vicky')
  * input('#password', 'aa')
  * submit().click("[type=submit]")
  # Accedemos al chat del tour al que asistimos como turista
  * click("[id=ChatAsistido1]")
  * match html('title') contains 'Chat'
  * input('#message', 'Mensajito de prueba')
  * driver.screenshot()
  * click("[id=sendmsg]")
  * driver.screenshot()
  * input('#message', 'con esto hago tiempo para que se envie el mensaje')
 #A continuacion cambiamos de usuario y accedemos al chat para ver que el mensaje ha llegado correctamente
  * click("a[id=salir]")
  * click("a[id=tour_ref]")
  * match html('title') contains 'Iniciar sesion'
  * input('#username', 'SPACEMARINE')
  * input('#password', 'aa')
  * submit().click("[type=submit]")
  * click("[id=chat1]")
  * match html('title') contains 'Chat'
  * driver.screenshot()
  * input('#message', 'Todo Ok')
  * driver.screenshot()
  * click("[id=sendmsg]")