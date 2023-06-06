# Inicializar proyecto 

## Descripción del proyecto

  El proyecto consiste en leer datos desde un archivo excel, luego volcar estos datos en una plantilla que contiene macros y formato de usuario, para posteriormente generar un nuevo archivo en un directorio de salida.

### 01-Desde github clonar este proyecto

	git clone https://github.com/eduardolinaresp/java_maven_excelreaderwriter.git
	 
### 02-acceder a direcorio java_maven_excelreaderwriter

	cd java_maven_excelreaderwriter

### 03-Restaurar dependencias y compilar

		Se asume que en el SO está instalado Maven.

#### 03.01-Remueve los .class generados (si existen), descargará las 	librerías según lo que se haya definido en las dependencias del pom.xml
		mvn clean

#### 03.02-Compila nuestro código. De los .java genera los .class

		mvn compile

#### 03.03-Compila las pruebas
	TODO..
<!---
		mvn test-compile
-->

#### 03.04-Ejecuta las pruebas.
	TODO...
<!---
		mvn test
-->
#### 03.05-Genera los archivos jar o war, según lo que se haya definido en el pom.xml.

		mvn install




