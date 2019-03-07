 
;;;; Programa de prueba de Lisp.
 (print "Bienvenido!")

 (defclass ciudadano ()
 	((nombre
 		:initarg :nombre
 		:initform (error "Tenés que tener nombre.")
 		:accessor ciudadano-nombre)
 	(edad 
 		:initarg :edad
 		:initform 0
 		:accessor ciudadano-edad)))



 (print "Cuál es tu nombre? ")

 (defvar *name* (read))
 (defvar *empadronado* nil)

 (defun hello-you (*name*)
 	(format t "Hola ~a! ~%" *name*))

 (hello-you *name*)  

(print "Cuántos años tenés? ")

(defvar *age* (read))

(defparameter *usuario* (make-instance 'ciudadano :nombre *name* :edad *age*))

(if (<= *age* 18)
	(progn
		(format t "Ya tenés edad para votar!  ~%")
		(format t "Ya estás empadronado? (1.Si / 2. No) ~%")
		(defparameter *empadronado* (read))
		(cond ((= *empadronado* 1)
			(format t "Ya estás listo para votar! ~%"))
			 (t (format t "Te tenés que empadronado si querés votar! ~%")))
	)
	(format t "Tenés que tener por lo menos 18 años para votar. ~%"))


		