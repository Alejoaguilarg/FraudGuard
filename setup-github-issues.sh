#!/bin/bash
# ============================================================
# FraudGuard — GitHub Issues Setup Script
# ============================================================
# Requisitos:
#   1. Tener gh CLI instalado: https://cli.github.com/
#   2. Estar autenticado: gh auth login
#   3. Tener el repo creado y estar en el directorio del repo
#      o pasar --repo=usuario/FraudGuard a cada comando
#
# Uso:
#   chmod +x setup-github-issues.sh
#   cd /ruta/a/tu/repo/FraudGuard
#   ./setup-github-issues.sh
# ============================================================

set -e

echo "🏗️  Creando labels..."

gh label create "epic:scaffolding"    --color "0E8A16" --description "Setup y estructura del proyecto" --force
gh label create "epic:dominio"        --color "1D76DB" --description "Lógica de negocio y reglas"      --force
gh label create "epic:endpoints"      --color "D93F0B" --description "Entry points y API REST"         --force
gh label create "epic:streaming"      --color "F9D0C4" --description "Tiempo real y documentación"     --force
gh label create "tipo:historia"       --color "FBCA04" --description "Historia de usuario"             --force
gh label create "prioridad:alta"      --color "B60205" --description "Crítico para el MVP"             --force
gh label create "prioridad:media"     --color "FF9F1C" --description "Importante pero no bloqueante"   --force
gh label create "prioridad:baja"      --color "C5DEF5" --description "Nice to have / opcional"         --force
gh label create "fase:1"              --color "BFD4F2" --description "Semana 1-2"                      --force
gh label create "fase:2"              --color "BFD4F2" --description "Semana 2-3"                      --force
gh label create "fase:3"              --color "BFD4F2" --description "Semana 3-4"                      --force
gh label create "fase:4"              --color "BFD4F2" --description "Semana 4-5"                      --force
gh label create "fase:5"              --color "BFD4F2" --description "Semana 5-6 (opcional)"           --force
gh label create "fase:6"              --color "BFD4F2" --description "Semana 6-7"                      --force

echo "📋 Creando milestones..."

gh api repos/{owner}/{repo}/milestones -f title="Fase 1 — Scaffolding + Dominio" -f description="Semana 1-2: Estructura del proyecto y modelos de dominio" -f due_on="2026-09-01T00:00:00Z" 2>/dev/null || echo "  Milestone Fase 1 ya existe"
gh api repos/{owner}/{repo}/milestones -f title="Fase 2 — Entry Points + Seguridad" -f description="Semana 2-3: WebFlux + OAuth2/JWT" -f due_on="2026-09-15T00:00:00Z" 2>/dev/null || echo "  Milestone Fase 2 ya existe"
gh api repos/{owner}/{repo}/milestones -f title="Fase 3 — Persistencia" -f description="Semana 3-4: R2DBC + perfil de comportamiento" -f due_on="2026-09-29T00:00:00Z" 2>/dev/null || echo "  Milestone Fase 3 ya existe"
gh api repos/{owner}/{repo}/milestones -f title="Fase 4 — LLM" -f description="Semana 4-5: Integración Ollama con salvaguardas" -f due_on="2026-10-13T00:00:00Z" 2>/dev/null || echo "  Milestone Fase 4 ya existe"
gh api repos/{owner}/{repo}/milestones -f title="Fase 5 — Streaming (opcional)" -f description="Semana 5-6: Simulador de transacciones" -f due_on="2026-10-27T00:00:00Z" 2>/dev/null || echo "  Milestone Fase 5 ya existe"
gh api repos/{owner}/{repo}/milestones -f title="Fase 6 — Documentación" -f description="Semana 6-7: README, diagramas, portafolio" -f due_on="2026-11-10T00:00:00Z" 2>/dev/null || echo "  Milestone Fase 6 ya existe"

echo ""
echo "🚀 Creando issues..."

# ============================================================
# EPIC 0 — SCAFFOLDING Y ESTRUCTURA
# ============================================================

gh issue create \
  --title "HU-00: Generar proyecto base con clean architecture reactiva" \
  --label "epic:scaffolding,tipo:historia,prioridad:alta,fase:1" \
  --milestone "Fase 1 — Scaffolding + Dominio" \
  --body '## Historia de Usuario
> Como *desarrollador*, quiero generar la estructura del proyecto usando el scaffold de Bancolombia con enfoque reactivo, para tener la separación de capas (domain/infrastructure/application) desde el inicio.

## Criterios de Aceptación
- [ ] El proyecto compila exitosamente con `./gradlew build`
- [ ] Estructura `domain/model`, `domain/usecase`, `infrastructure/`, `applications/app-service` creada
- [ ] El proyecto usa Java 21, Lombok, tipo reactivo
- [ ] `.gitignore` configurado correctamente

## Tareas Técnicas
- [ ] Crear directorio del proyecto
- [ ] Configurar `build.gradle` con plugin `co.com.bancolombia.cleanArchitecture` v4.5.0
- [ ] Ejecutar: `gradle ca --package=co.com.fraudguard --type=reactive --name=FraudGuard --lombok=true --java-version=21`
- [ ] Verificar compilación: `./gradlew build`
- [ ] Inicializar repositorio git + primer commit

## Comando del Scaffold
```bash
gradle ca --package=co.com.fraudguard --type=reactive --name=FraudGuard --lombok=true --java-version=21
```

## Estimación
~1h'

echo "  ✅ HU-00 creada"

gh issue create \
  --title "HU-01: Generar modelos de dominio" \
  --label "epic:scaffolding,tipo:historia,prioridad:alta,fase:1" \
  --milestone "Fase 1 — Scaffolding + Dominio" \
  --body '## Historia de Usuario
> Como *desarrollador*, quiero crear las entidades del dominio usando el generador del scaffold, para que vivan en `domain/model` aisladas de infraestructura.

## Criterios de Aceptación
- [ ] Entidades generadas en `domain/model` con sus atributos completos
- [ ] Puertos (interfaces) definidos en `domain/model`
- [ ] No hay dependencias de infraestructura en la capa de dominio

## Tareas Técnicas
- [ ] Ejecutar: `./gradlew gm --name=Transaccion`
- [ ] Ejecutar: `./gradlew gm --name=PerfilComportamiento`
- [ ] Ejecutar: `./gradlew gm --name=EntidadRestringida`
- [ ] Ejecutar: `./gradlew gm --name=Alerta`
- [ ] Enriquecer `Transaccion`: monto, cuentaOrigen, cuentaDestino, timestamp, canal, geolocalizacion (lat/long), deviceId, ip, referencia, descripcion
- [ ] Enriquecer `PerfilComportamiento`: cuentaId, montoPromedio, horariosHabituales, ubicacionesRecurrentes, dispositivosConocidos
- [ ] Enriquecer `EntidadRestringida`: id, tipo (DEVICE/IP), valor, motivo, fechaRegistro, nivelConfianza (TEMPORAL/PERMANENTE)
- [ ] Enriquecer `Alerta`: id, transaccionId, score, explicacionLlm, reglasDisparadas, estado (APROBADA/SOSPECHOSA/FRAUDE_CONFIRMADO), timestamp, consultadoPor
- [ ] Crear interfaz `TransaccionRepositoryPort`
- [ ] Crear interfaz `ListaRestringidaPort` (estaRestringido, registrarRestriccion)
- [ ] Crear interfaz `AnalizadorFraudePort`
- [ ] Crear interfaz `PerfilComportamientoPort`

## Estimación
~3h'

echo "  ✅ HU-01 creada"

gh issue create \
  --title "HU-02: Generar casos de uso" \
  --label "epic:scaffolding,tipo:historia,prioridad:alta,fase:1" \
  --milestone "Fase 1 — Scaffolding + Dominio" \
  --body '## Historia de Usuario
> Como *desarrollador*, quiero crear los casos de uso del sistema para que orquesten la lógica de evaluación de transacciones desde `domain/usecase`.

## Criterios de Aceptación
- [ ] Casos de uso generados en `domain/usecase`
- [ ] Cada caso de uso recibe sus dependencias (puertos) por inyección de constructor
- [ ] Tests unitarios con mocks de los puertos

## Tareas Técnicas
- [ ] Ejecutar: `./gradlew guc --name=EvaluarTransaccion`
- [ ] Ejecutar: `./gradlew guc --name=ConsultarAlerta`
- [ ] Ejecutar: `./gradlew guc --name=GestionarListaRestringida`
- [ ] `EvaluarTransaccion`: orquesta chequeo denylist → reglas → LLM → clasificación → auto-enrolamiento/reversión
- [ ] `ConsultarAlerta`: consulta historial de alertas por cuenta (paginado reactivo)
- [ ] `GestionarListaRestringida`: confirmar/liberar restricciones (admin)
- [ ] Tests unitarios para cada caso de uso

## Estimación
~3h'

echo "  ✅ HU-02 creada"

gh issue create \
  --title "HU-03: Generar entry point WebFlux con autorización" \
  --label "epic:scaffolding,tipo:historia,prioridad:alta,fase:2" \
  --milestone "Fase 2 — Entry Points + Seguridad" \
  --body '## Historia de Usuario
> Como *desarrollador*, quiero generar el entry point WebFlux con autorización habilitada, para exponer los endpoints protegidos por OAuth2/JWT.

## Criterios de Aceptación
- [ ] Entry point WebFlux generado con autorización habilitada
- [ ] Scopes configurados: `fraude:read`, `fraude:write`, `fraude:admin`
- [ ] JWT inválido o sin scope adecuado → 401/403
- [ ] Campos de texto libre se sanitizan antes de pasar al caso de uso

## Tareas Técnicas
- [ ] Ejecutar: `./gradlew gep --type=webflux --authorization=true`
- [ ] Configurar OAuth2 Resource Server en `application.yaml` (Keycloak local para pruebas)
- [ ] Definir SecurityConfig con scopes por endpoint
- [ ] Implementar sanitización de campos `referencia` y `descripcion` en el Handler
- [ ] Definir rutas en RouterRest:
  - `POST /api/transacciones/evaluar` → scope `fraude:write`
  - `GET /api/alertas` → scope `fraude:read`
  - `PUT /api/restricciones/{id}` → scope `fraude:admin`
- [ ] Tests de integración con JWT válido/inválido/sin scope

## Comando del Scaffold
```bash
./gradlew gep --type=webflux --authorization=true
```

## Estimación
~4h'

echo "  ✅ HU-03 creada"

gh issue create \
  --title "HU-04: Generar driven adapter R2DBC (persistencia reactiva)" \
  --label "epic:scaffolding,tipo:historia,prioridad:alta,fase:3" \
  --milestone "Fase 3 — Persistencia" \
  --body '## Historia de Usuario
> Como *desarrollador*, quiero generar el adaptador de persistencia reactiva con R2DBC para PostgreSQL, para implementar los puertos de repositorio del dominio.

## Criterios de Aceptación
- [ ] Driven adapter R2DBC generado e implementando los puertos del dominio
- [ ] Tablas: transacciones, alertas, entidades_restringidas, perfiles_comportamiento
- [ ] Restricciones de IP tienen TTL configurable
- [ ] Trazabilidad: cada alerta guarda quién la consultó

## Tareas Técnicas
- [ ] Ejecutar: `./gradlew gda --type=r2dbc`
- [ ] Implementar `TransaccionRepositoryPort`
- [ ] Implementar `ListaRestringidaPort` con TTL para IP (24-48h configurable)
- [ ] Implementar `PerfilComportamientoPort` (consulta/actualización de baseline)
- [ ] Crear scripts de migración SQL (schema.sql)
- [ ] Configurar docker-compose con PostgreSQL para desarrollo local
- [ ] Tests de integración con Testcontainers

## Comando del Scaffold
```bash
./gradlew gda --type=r2dbc
```

## Estimación
~5h'

echo "  ✅ HU-04 creada"

gh issue create \
  --title "HU-05: Generar driven adapter para LLM (Ollama)" \
  --label "epic:scaffolding,tipo:historia,prioridad:alta,fase:4" \
  --milestone "Fase 4 — LLM" \
  --body '## Historia de Usuario
> Como *desarrollador*, quiero crear un driven adapter para la integración con Ollama (Llama 3/Mistral), para que el dominio no dependa de la tecnología de IA específica.

## Criterios de Aceptación
- [ ] Adaptador implementa `AnalizadorFraudePort`
- [ ] Recibe solo señales estructuradas (nunca texto libre crudo del usuario)
- [ ] Nunca retorna un score menor al calculado por reglas deterministas
- [ ] Genera explicación en lenguaje natural para auditoría

## Tareas Técnicas
- [ ] Ejecutar: `./gradlew gda --type=restconsumer --url=http://localhost:11434`
- [ ] Implementar `AnalizadorFraudePort` como adaptador REST hacia Ollama
- [ ] Diseñar prompt defensivo: recibe señales estructuradas (reglas disparadas, score, contexto sanitizado)
- [ ] Validación post-respuesta: si score del LLM < score de reglas → usar score de reglas
- [ ] Parsear respuesta del LLM: extraer explicación + score complementario
- [ ] Manejo de timeout/fallback: si Ollama no responde, usar solo resultado de reglas
- [ ] Tests unitarios con mock de Ollama (WireMock)
- [ ] Documentar el prompt template usado

## Comando del Scaffold
```bash
./gradlew gda --type=restconsumer --url=http://localhost:11434
```

## Estimación
~6h'

echo "  ✅ HU-05 creada"

# ============================================================
# EPIC 1 — LÓGICA DE NEGOCIO (DOMINIO)
# ============================================================

gh issue create \
  --title "HU-06: Implementar chequeo de lista restringida (fail-fast)" \
  --label "epic:dominio,tipo:historia,prioridad:alta,fase:1" \
  --milestone "Fase 1 — Scaffolding + Dominio" \
  --body '## Historia de Usuario
> Como *sistema*, quiero verificar si el dispositivo o IP de una transacción entrante ya está en la lista restringida, para rechazarla inmediatamente sin gastar cómputo en reglas ni LLM.

## Criterios de Aceptación
- [ ] Si `device_id` está en denylist → rechazo inmediato con motivo
- [ ] Si `IP` está en denylist → rechazo inmediato con motivo
- [ ] No se ejecutan reglas deterministas ni se invoca al LLM
- [ ] La respuesta incluye el motivo original de la restricción

## Tareas Técnicas
- [ ] Implementar lógica en `EvaluarTransaccionUseCase` como primer paso del flujo
- [ ] Llamar a `ListaRestringidaPort.estaRestringido(deviceId, DEVICE)` y `estaRestringido(ip, IP)`
- [ ] Si alguno retorna true → retornar `Mono.error()` o resultado de rechazo inmediato
- [ ] Test: transacción con device restringido → rechazada sin ejecutar reglas
- [ ] Test: transacción con IP restringida → rechazada sin ejecutar reglas
- [ ] Test: transacción limpia → continúa al siguiente paso

## Estimación
~1.5h'

echo "  ✅ HU-06 creada"

gh issue create \
  --title "HU-07: Implementar reglas deterministas de fraude" \
  --label "epic:dominio,tipo:historia,prioridad:alta,fase:1" \
  --milestone "Fase 1 — Scaffolding + Dominio" \
  --body '## Historia de Usuario
> Como *sistema*, quiero evaluar cada transacción contra reglas deterministas robustas, para detectar patrones de fraude conocidos antes de involucrar al LLM.

## Criterios de Aceptación
- [ ] Cada regla es independiente, testeable y configurable
- [ ] El resultado es un score numérico + lista de reglas disparadas
- [ ] Si ninguna regla se dispara → transacción aprobada (no se llama al LLM)

## Reglas a Implementar

### R1 — Velocity (frecuencia)
- [ ] Detectar N transacciones de la misma cuenta en ventana de T minutos
- [ ] N y T configurables via properties
- [ ] Test: 5 transacciones en 10 min → dispara; 2 en 10 min → no dispara

### R2 — Smurfing / Estructuración
- [ ] Detectar transacciones pequeñas que sumadas superan umbral en ventana de tiempo
- [ ] Umbral configurable (ej. 10M COP en 24h)
- [ ] Test: 20 transacciones de 600K en 24h = 12M → dispara

### R3 — Impossible Travel
- [ ] Calcular distancia (Haversine) entre ubicación de transacción actual y última transacción
- [ ] Calcular tiempo transcurrido entre ambas
- [ ] Si velocidad implícita > 900 km/h → dispara
- [ ] Test: Medellín → Bogotá en 15 min → dispara; en 2h → no dispara

### R4 — Desviación de Baseline
- [ ] Comparar monto vs promedio de la cuenta (desviación estándar configurable)
- [ ] Comparar horario vs horarios habituales
- [ ] Comparar ubicación vs ubicaciones recurrentes
- [ ] Test: transacción 10x el promedio → dispara

### R5 — Dispositivo/IP nuevo
- [ ] Verificar si device_id o IP no están en los registrados para esa cuenta
- [ ] Test: device nuevo + monto normal → dispara (score bajo); device nuevo + monto alto → dispara (score alto)

## Estimación
~5h'

echo "  ✅ HU-07 creada"

gh issue create \
  --title "HU-08: Implementar scoring combinado (reglas + LLM)" \
  --label "epic:dominio,tipo:historia,prioridad:alta,fase:4" \
  --milestone "Fase 4 — LLM" \
  --body '## Historia de Usuario
> Como *sistema*, quiero combinar el score de las reglas deterministas con el análisis del LLM, para clasificar la transacción en uno de tres estados finales.

## Criterios de Aceptación
- [ ] El LLM solo se invoca si al menos una regla determinista se disparó
- [ ] El LLM nunca puede reducir el score que ya calcularon las reglas
- [ ] Tres estados finales: APROBADA / SOSPECHOSA / FRAUDE_CONFIRMADO
- [ ] Los umbrales de clasificación son configurables

## Tareas Técnicas
- [ ] Definir umbrales en properties: `score.sospechoso=50`, `score.fraude=85`
- [ ] Implementar lógica de combinación: `scoreFinal = max(scoreReglas, scoreLlm)`
- [ ] Si scoreFinal < umbral sospechoso → APROBADA
- [ ] Si scoreFinal >= umbral sospechoso y < umbral fraude → SOSPECHOSA
- [ ] Si scoreFinal >= umbral fraude → FRAUDE_CONFIRMADO
- [ ] Persistir resultado con explicación del LLM + reglas disparadas
- [ ] Test: score reglas 70 + score LLM 40 → scoreFinal 70 (no baja)
- [ ] Test: score reglas 60 + score LLM 90 → scoreFinal 90 → FRAUDE_CONFIRMADO
- [ ] Test: ninguna regla disparada → APROBADA sin llamar al LLM

## Estimación
~2h'

echo "  ✅ HU-08 creada"

gh issue create \
  --title "HU-09: Implementar auto-enrolamiento en denylist y reversión" \
  --label "epic:dominio,tipo:historia,prioridad:alta,fase:4" \
  --milestone "Fase 4 — LLM" \
  --body '## Historia de Usuario
> Como *sistema*, cuando una transacción se confirma como fraude, quiero registrar automáticamente el dispositivo/IP en la lista restringida y disparar la reversión si ya fue procesada.

## Criterios de Aceptación
- [ ] FRAUDE_CONFIRMADO → device_id se registra como restricción PERMANENTE
- [ ] FRAUDE_CONFIRMADO → IP se registra como restricción TEMPORAL (TTL configurable)
- [ ] Si la transacción ya fue procesada → se emite evento de reversión
- [ ] Si la transacción aún no fue procesada → se bloquea (no se procesa)
- [ ] La restricción de IP queda pendiente de confirmación de analista

## Tareas Técnicas
- [ ] Implementar auto-enrolamiento en `EvaluarTransaccionUseCase` como paso final
- [ ] Crear evento de dominio `TransaccionMarcadaFraudulenta`
- [ ] Crear puerto `ReversionPort` con operación `solicitarReversion(transaccionId)`
- [ ] Implementar adaptador mock de reversión (solo loguea la solicitud)
- [ ] Test: fraude confirmado → device registrado como PERMANENTE
- [ ] Test: fraude confirmado → IP registrada como TEMPORAL
- [ ] Test: transacción ya procesada + fraude → evento de reversión emitido
- [ ] Test: transacción no procesada + fraude → bloqueada sin reversión

## Estimación
~3h'

echo "  ✅ HU-09 creada"

# ============================================================
# EPIC 2 — ENDPOINTS DE NEGOCIO
# ============================================================

gh issue create \
  --title "HU-10: Endpoint de evaluación de transacciones" \
  --label "epic:endpoints,tipo:historia,prioridad:alta,fase:2" \
  --milestone "Fase 2 — Entry Points + Seguridad" \
  --body '## Historia de Usuario
> Como *app bancaria*, quiero enviar transacciones al endpoint `POST /api/transacciones/evaluar` con scope `fraude:write`, para obtener el resultado de la evaluación en tiempo real.

## Criterios de Aceptación
- [ ] `POST /api/transacciones/evaluar` acepta JSON con todos los campos de Transaccion
- [ ] Requiere JWT con scope `fraude:write`
- [ ] Sin JWT o scope incorrecto → 401/403
- [ ] Campos de texto libre sanitizados antes de procesamiento
- [ ] Respuesta incluye: estado (APROBADA/SOSPECHOSA/FRAUDE_CONFIRMADO), score, explicación (si aplica)

## Tareas Técnicas
- [ ] Implementar Handler con validación de entrada
- [ ] Implementar sanitización de campos `referencia` y `descripcion`
- [ ] Mapeo DTO → dominio (TransaccionRequest → Transaccion)
- [ ] Mapeo dominio → DTO (Alerta → AlertaResponse)
- [ ] Integración con `EvaluarTransaccionUseCase`
- [ ] Test de integración con JWT válido → 200
- [ ] Test de integración sin JWT → 401
- [ ] Test de integración con scope incorrecto → 403
- [ ] Test con campos de texto malicioso → sanitizados

## Estimación
~3h'

echo "  ✅ HU-10 creada"

gh issue create \
  --title "HU-11: Endpoint de consulta de alertas" \
  --label "epic:endpoints,tipo:historia,prioridad:media,fase:2" \
  --milestone "Fase 2 — Entry Points + Seguridad" \
  --body '## Historia de Usuario
> Como *analista de cumplimiento*, quiero consultar alertas por cuenta vía `GET /api/alertas?cuenta={id}` con scope `fraude:read`, para documentar decisiones ante el regulador.

## Criterios de Aceptación
- [ ] `GET /api/alertas?cuenta={id}` retorna alertas paginadas (Flux)
- [ ] Requiere scope `fraude:read`
- [ ] Cada alerta incluye: score, explicación del LLM, reglas disparadas, timestamp, consultadoPor
- [ ] Sin cargar todo en memoria (streaming reactivo real)

## Tareas Técnicas
- [ ] Implementar ruta en RouterRest
- [ ] Implementar Handler con paginación reactiva
- [ ] Registrar quién consultó (extraer del JWT)
- [ ] Test: consulta con cuenta válida → retorna alertas
- [ ] Test: consulta sin scope → 403

## Estimación
~2h'

echo "  ✅ HU-11 creada"

gh issue create \
  --title "HU-12: Endpoint de administración de lista restringida" \
  --label "epic:endpoints,tipo:historia,prioridad:media,fase:2" \
  --milestone "Fase 2 — Entry Points + Seguridad" \
  --body '## Historia de Usuario
> Como *analista de cumplimiento*, quiero confirmar o liberar restricciones de IP vía `PUT /api/restricciones/{id}` con scope `fraude:admin`, para evitar falsos positivos en IPs compartidas.

## Criterios de Aceptación
- [ ] `PUT /api/restricciones/{id}` permite confirmar (TEMPORAL → PERMANENTE) o liberar (eliminar)
- [ ] Requiere scope `fraude:admin`
- [ ] `GET /api/restricciones?tipo={DEVICE|IP}&estado={PENDIENTE|CONFIRMADA}` para listar
- [ ] Solo restricciones de IP pueden ser liberadas; las de dispositivo son permanentes

## Tareas Técnicas
- [ ] Implementar rutas en RouterRest
- [ ] Implementar Handler para confirmar/liberar
- [ ] Implementar Handler para listar con filtros
- [ ] Integración con `GestionarListaRestringidaUseCase`
- [ ] Test: confirmar IP pendiente → cambia a PERMANENTE
- [ ] Test: liberar IP → eliminada
- [ ] Test: intentar liberar DEVICE → rechazado
- [ ] Test: sin scope admin → 403

## Estimación
~2h'

echo "  ✅ HU-12 creada"

# ============================================================
# EPIC 3 — STREAMING Y DOCUMENTACIÓN (OPCIONAL)
# ============================================================

gh issue create \
  --title "HU-13: Simulador de transacciones en tiempo real" \
  --label "epic:streaming,tipo:historia,prioridad:baja,fase:5" \
  --milestone "Fase 5 — Streaming (opcional)" \
  --body '## Historia de Usuario
> Como *desarrollador*, quiero simular un flujo continuo de transacciones, para probar el comportamiento del sistema bajo carga similar a producción.

## Criterios de Aceptación
- [ ] Generador emite transacciones sintéticas cada N segundos (configurable)
- [ ] Mezcla de transacciones legítimas y sospechosas (con patrones de fraude)
- [ ] El sistema procesa sin bloquear el hilo principal (reactivo real)
- [ ] Evento `TransaccionMarcadaFraudulenta` dispara reversión mock

## Tareas Técnicas
- [ ] Crear generador con `Flux.interval` o Kafka producer
- [ ] Definir dataset de transacciones sintéticas con patrones:
  - Transacciones normales (70%)
  - Velocity attack (10%)
  - Smurfing (10%)
  - Impossible travel (5%)
  - Device nuevo + monto alto (5%)
- [ ] Conectar generador al endpoint de evaluación
- [ ] Monitorear logs de alertas generadas

## Estimación
~4h'

echo "  ✅ HU-13 creada"

gh issue create \
  --title "HU-14: Documentación técnica y README de portafolio" \
  --label "epic:streaming,tipo:historia,prioridad:media,fase:6" \
  --milestone "Fase 6 — Documentación" \
  --body '## Historia de Usuario
> Como *desarrollador*, quiero documentar la arquitectura, decisiones de diseño y flujo completo del sistema, para tener un repositorio listo para mostrar en entrevistas.

## Criterios de Aceptación
- [ ] README.md completo con secciones de arquitectura, setup, y ejecución
- [ ] Diagrama de arquitectura hexagonal (draw.io o Mermaid)
- [ ] Diagrama de flujo: denylist → reglas → LLM → clasificación → reversión
- [ ] Sección de amenazas IA (prompt injection, deepfakes en auth)
- [ ] Instrucciones de setup local (Docker Compose, Keycloak, Ollama)

## Tareas Técnicas
- [ ] Escribir README.md con:
  - Descripción del proyecto y problema que resuelve
  - Stack tecnológico y justificación
  - Diagrama de arquitectura hexagonal
  - Flujo de evaluación de transacciones
  - Reglas de detección implementadas
  - Integración con LLM (Ollama) y salvaguardas
  - Sección de seguridad: OAuth2/JWT + sanitización + prompt injection
  - Setup local paso a paso
- [ ] Crear `docker-compose.yml` completo (Postgres, Keycloak, Ollama)
- [ ] Crear diagrama Mermaid del flujo de evaluación
- [ ] Documentar decisiones de diseño (ADRs opcionales)

## Estimación
~4h'

echo "  ✅ HU-14 creada"

echo ""
echo "============================================================"
echo "✅ ¡Listo! 15 issues creados con labels y milestones."
echo ""
echo "Siguiente paso: ve a GitHub → tu repo → Projects → New Project"
echo "→ Board view → agrega todos los issues al proyecto."
echo "============================================================"
