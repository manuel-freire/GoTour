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
   #vamos a la pagina de inicio para acceder a un tour
  * submit().click("[type=submit]")
  * match html('title') contains 'Perfil'
  * click("a[id=logo_box]")
  * match html('title') contains 'GoTour'
  * driver.screenshot()
  #accedemos al tour
  * click("a[id=tour_ref]")
  * match html('title') contains 'Tour'
  #Nos apuntamos al tour y lo pagamos
  #* input('#turistas', "1")
  * submit().click("[type=submit]")
  * driver.screenshot()
  * match html('title') contains 'Pago'
  * driver.screenshot()
  * click("[id=enviarPago]")
  * match html('title') contains 'GoTour'
  * driver.screenshot()
  # Volvemos al tour para hacer una review sobre el mismo
  * click("a[id=tour_ref]")
  * match html('title') contains 'Tour'
  * click("a[id=hacer_review]")
  * match html('title') contains 'Valoracion del tour'
  * driver.screenshot()
  * value('#valoracion', '')
  * input('#valoracion', '3')
  * input('#textoReview', 'Un treh porque el guia era mu soso aunque el tour ha sido mu bonito')
  * submit().click("[type=submit]")
  * match html('title') contains 'GoTour'
  * driver.screenshot()
