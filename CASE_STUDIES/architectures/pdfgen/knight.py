"""KnightMotives Automotive target architecture."""

from render import banded, columns, figure, flow, note, table

ACCENT = "#0f172a"
ACCENT2 = "#d97706"
BAND = "#e8eef5"


def document() -> dict:
    layers = banded(
        [
            (
                "Experience",
                [
                    ("Drivers", "One account, every model"),
                    ("Dealers", "Browser tools, no new hardware"),
                    ("Plants", "Shop floor and logistics"),
                    ("Engineers", "Autonomy, in allowed regions"),
                ],
            ),
            (
                "Vehicle edge",
                [
                    ("On-vehicle AI", "Safety and baseline UX onboard"),
                    ("Store and forward", "Rural links are optional"),
                    ("Signed OTA", "Staged rollout and rollback"),
                    ("Head-unit bridge", "Legacy models, same account"),
                ],
            ),
            (
                "Cloud experience",
                [
                    ("Apigee", "One vehicle and dealer API"),
                    ("Cloud Run + GKE", "Build-to-order and CRM"),
                    ("Cloud SQL", "Orders, profiles, dealer work"),
                    ("Read model", "Fast status when ERP is slow"),
                ],
            ),
            (
                "Enterprise core",
                [
                    ("Promotions service", "Offers the ERP cannot express"),
                    ("Anti-corruption layer", "Mainframe and ERP stay"),
                    ("Interconnect", "Plants and headquarters"),
                    ("GDC at plants", "Work that must survive a WAN cut"),
                ],
            ),
            (
                "Data and autonomy",
                [
                    ("Pub/Sub + Dataflow", "Telemetry ingest"),
                    ("Dataplex + BigQuery", "Zones, lineage, marts"),
                    ("Vertex AI", "Maintenance models and training"),
                    ("Analytics Hub", "Aggregated products only"),
                ],
            ),
            (
                "Trust",
                [
                    ("Assured Workloads", "EU data stays in the EU"),
                    ("VPC-SC + CMEK", "Perimeter after prior breaches"),
                    ("Security Operations", "Detection and response"),
                    ("PAM + IAP", "Zero trust for staff and dealers"),
                ],
            ),
        ],
        ACCENT,
        BAND,
    )
    order = flow(
        [
            ("Configure", "Options and a price band"),
            ("Commit", "Order in Cloud SQL"),
            ("Show", "Dealer and buyer see one status"),
            ("Post", "Outbox writes ERP and mainframe"),
            ("Reconcile", "ERP events update the read model"),
        ],
        ACCENT,
    )
    vehicle = flow(
        [
            ("Drive", "Onboard model, cached profile"),
            ("Buffer", "Priority queue on the vehicle"),
            ("Sync", "HTTPS or MQTT when the link exists"),
            ("Use", "Service lead, product, or OTA"),
        ],
        ACCENT,
    )
    plants = columns(
        [
            (
                "Plant and HQ",
                [
                    "OT network stays segmented",
                    "Broker is the only crossing",
                    "GDC for partition-tolerant jobs",
                    "No new dealer hardware",
                ],
            ),
            (
                "Network",
                [
                    "Interconnect where volume warrants",
                    "HA VPN for smaller sites",
                    "Cloud Router and BGP",
                    "Private access to Google APIs",
                ],
            ),
            (
                "Cloud regions",
                [
                    "EU folder for EU vehicles and staff",
                    "US folder for US operations",
                    "Autonomy projects only where lawful",
                    "Analytics Hub at the boundary",
                ],
            ),
        ],
        ACCENT,
        BAND,
    )

    body = f"""
<h2>1. Purpose</h2>
<p>This paper is the target architecture for KnightMotives Automotive. KnightMotives builds autonomous-capable battery-electric vehicles, hybrids, and internal-combustion cars. The in-vehicle experience on the BEV fleet has moved ahead of the hybrid and ICE fleets, and those older fleets are losing sales. The online build-to-order path is unreliable. Dealers do not get the data they need, and they have no budget for new equipment. Supply chain still runs on an outdated mainframe. ERP changes make promotions and dealer discounts slow. Vehicle software is fragmented, with technical debt from backward compatibility. Plants and rural roads are hard to connect. Corporate data is siloed, the existing AI infrastructure is obsolete, and past breaches make security a board-level constraint. European data-protection rules apply, especially to autonomous platforms.</p>
<p>The five-year goal is one automotive experience: the same relationship with the driver on every model, a build-to-order path dealers and buyers can trust, tools for sales and service that run in a browser, a data platform that can fund the program through carefully bounded monetization, and an autonomy program that starts only in regions whose rules allow it. Safety-relevant driving behavior stays on the vehicle. The cloud makes the product coherent, the commerce path reliable, and the data usable.</p>

<h2>2. Assumptions</h2>
{table(
    ["Topic", "Assumption used in this design"],
    [
        ["Safety", "Anything that affects control of the car runs on the vehicle. Cloud AI personalizes, informs, and updates. It is not in the control loop."],
        ["Connectivity", "Rural and plant links fail. Every vehicle and every plant workflow that matters has a defined offline behavior."],
        ["Dealers", "Dealers will use a browser on the PCs they already own. The architecture does not ship appliances."],
        ["Systems of record", "The mainframe and ERP remain the systems of record during this program. Cloud systems own the shopper acknowledgement and a read model, and they post to ERP through an outbox."],
        ["Law", "EU personal data, including vehicle location and cabin-adjacent data, is processed in an EU Assured Workloads folder. Autonomy training runs only in a project whose region is cleared for that program."],
        ["Monetization", "External products are aggregated and approved. Raw telemetry, direct identifiers, and precise tracks are not a product."],
    ],
)}

<h2>3. Requirements and the response</h2>
{table(
    ["ID", "Requirement", "Architecture response"],
    [
        ["BR1", "A personal relationship and one experience on every model", "One driver account and one Apigee API. Capability flags by head-unit generation. Legacy units get the subset they can render."],
        ["BR2", "Reliable build-to-order and transparency", "Cloud SQL order, one status read model, asynchronous post to ERP."],
        ["BR3", "Fund the program from corporate data", "Dataplex zones and Analytics Hub listings of approved aggregates. Internal monetization includes predictive service."],
        ["BR4", "Security and EU protection, including autonomy", "Zero trust, perimeter, CMEK, Security Operations, EU folder, isolated autonomy projects."],
        ["BR5", "Autonomy, first where regulation allows", "Vertex AI and simulation in a cleared project, with a promotion gate before any vehicle build."],
        ["BR6", "Upskilling and a shared business–engineering picture", "A platform team, paved modules, and Looker SLOs visible to commerce and engineering."],
        ["TR1", "Consistent in-vehicle UX and AI, including legacy hardware", "Signed OTA, a head-unit bridge, onboard inference, cloud features when the link exists."],
        ["TR2", "Connectivity for real-time AI, including rural use", "On-vehicle buffer with priorities. Real-time cloud features are additive. Safety does not wait on the radio."],
        ["TR3", "Plant and headquarters networks", "Interconnect for large sites, HA VPN for small ones, OT segmentation, GDC where the plant must run through a WAN cut."],
        ["TR4", "Hybrid cloud and gradual legacy modernization", "Anti-corruption layer in front of mainframe and ERP. Dual Run is a later, assessed step, not the opening move."],
        ["TR5", "Autonomy development, simulation, and compliance", "Vertex AI Pipelines, Batch or GKE GPUs, Model Registry, lineage, and a recorded safety gate."],
        ["TR6", "Data platform, privacy, scalable AI/ML", "Dataplex, BigQuery, CMEK, de-identification, Vertex AI as the replacement for the obsolete AI stack."],
        ["TR7", "Security framework, response, and awareness", "Security Command Center Premium, Security Operations, an incident plan with drills, and a training obligation tracked as a control."],
        ["TR8", "Dealer sales, service, inventory, and CRM", "Browser portal, CRM on Cloud Run and Cloud SQL, customer 360 in BigQuery, inventory read model fed by ERP events."],
    ],
    id_col=True,
)}

<h2>4. Current state</h2>
<p>IT is mostly on-premises, with some applications already on major clouds. That split, plus a mainframe and an ERP that resist change, is why a promotion or a dealer discount is a project. The ordering site fails in public, and dealers lose trust even when the factory is fine. Vehicle codebases differ by model, so an experience that exists on the BEV cannot be assumed on the ICE car. Rural coverage and plant WANs are physical facts. The architecture treats them as constraints, and it treats the breach history as a reason to default to private access, short-lived privilege, and retained detections.</p>

<h2>5. Target architecture</h2>
<p>Four products share a platform and do not share a fate. The <strong>car</strong> works with the radio down. The <strong>shop</strong> acknowledges a build-to-order even when ERP is slow. The <strong>plant</strong> keeps shipping if the WAN drops. The <strong>lab</strong> trains only where it is allowed to train. Dataplex is the governance plane across them. Apigee is the only API dealers, vehicles, and partners see.</p>
{figure(layers, "Figure 1. KnightMotives target architecture. On-vehicle behavior, commerce, plants, and autonomy are separate planes on one governed platform.")}
{note("<strong>Offline is a feature.</strong> A rural hybrid still books service, still shows the last synced profile, and still drives on its onboard model. The cloud catches up when the vehicle next connects. Priority on the buffer is safety diagnostics, then service, then infotainment.")}

<h3>One experience on unequal hardware</h3>
{figure(vehicle, "Figure 2. The vehicle is the real-time system. The cloud is the system of engagement.")}
<p>New head units run one experience platform. Older units run a bridge that speaks the same Apigee contract and renders the subset the hardware can support: account, service booking, remote status, and content packs, even when the full conversational interface does not fit. OTA artifacts are signed, stored in Cloud Storage, delivered through Cloud CDN, rolled out in stages, and reversible. Models that personalize cabin experience download as versioned artifacts. Models that affect driving are promoted only through the autonomy gate and are not silently updated from a chat prompt.</p>
<p>Ingestion after Cloud IoT Core’s retirement is an HTTPS and MQTT gateway on GKE in the vehicle’s home region. The gateway publishes to Pub/Sub. Dataflow lands telemetry in BigQuery. The vehicle authenticates with a hardware-backed identity. Cabin audio and precise location are off unless the driver has granted that purpose, and the grant is enforced in the gateway, not only in a privacy policy.</p>

<h3>Build-to-order, dealers, and CRM</h3>
{figure(order, "Figure 3. The buyer’s acknowledgement is a cloud commit. ERP is updated by an outbox, then reconciled.")}
<p>The configurator runs on Cloud Run behind the global load balancer and Cloud Armor. Options and constraints come from a catalog service, not from a screen-scrape of ERP. Price comes from the promotions service: ERP remains the base price, and the cloud service applies the dealer discount and the campaign the ERP cannot represent. The order insert into regional Cloud SQL HA is the moment the buyer and the dealer can both see a status. An outbox publisher posts the order to the mainframe and ERP through the anti-corruption layer. If ERP is down, the status stays Accepted, pending factory confirm, and the dealer portal does not spin. When ERP emits events, the read model in Cloud SQL updates. Inventory, allocation, and service-bay data use the same pattern: ERP is authoritative, the portal reads a model that is allowed to be seconds behind and is not allowed to be blank.</p>
<p>The dealer portal is a web application. Sales, service, and inventory are modules on it. CRM is operational data in Cloud SQL (people, opportunities, service relationships) and a customer 360 in BigQuery for the history a salesperson should see. Looker tiles embed in the portal so the business and the platform team are looking at the same order SLO. Dealers sign in with Identity Platform federated to the dealer identity KnightMotives already governs, through IAP, on machines they already have.</p>

<h3>Plants, mainframe, and ERP</h3>
{figure(plants, "Figure 4. Plants join the cloud privately. Shop-floor survival and dealer desktops are different problems.")}
<p>Large plants and headquarters use Dedicated Interconnect or Partner Interconnect. Smaller sites use HA VPN until their traffic justifies a circuit. OT networks stay segmented; a broker crosses into IT with a named schema, and the plant VPC is not a flat extension of the office. Google Distributed Cloud connected runs the plant-local workloads that must keep operating through a WAN partition. It is not a second home for ERP.</p>
<p>The mainframe and ERP gain an anti-corruption layer on GKE: idempotent commands, an outbox, and translated events. The promotions service is the first workload in front of ERP because that is the change the business cannot make today. Broader mainframe modernization, including Dual Run where an assessment shows the workload is worth a parallel run, is a later phase. Replacing ERP in year one would put the ordering recovery behind a second, larger program.</p>

<h3>Data, monetization, and maintenance</h3>
<p>Dataplex organizes zones. Restricted zones hold direct identifiers, precise location, and any cabin data under a consent flag, with CMEK and column policies. Operational zones hold diagnostics and build-to-order facts. Monetizable zones hold aggregates that have passed a review: k-anonymity thresholds, dropped identifiers, and a named owner. Analytics Hub publishes only from the monetizable zone. A listing is a product with a data contract. Vertex AI Feature Store and BigQuery ML, then full Vertex training where the model needs it, replace the obsolete AI stack. The first model on the platform is predictive maintenance: telemetry in, a component-risk score out, a lead in the dealer service module. That is monetization the company can use before it sells an external dataset, and it is entirely inside the residency of the vehicle.</p>

<h3>Autonomy</h3>
<p>The autonomy program is a separate folder and project per cleared region. Sensor data lands in Cloud Storage with lifecycle to colder classes after the active training window. Vertex AI Pipelines train and evaluate. Simulation runs on Batch or on GKE GPU node pools, using spot capacity where a run can restart, and reserved capacity where a campaign cannot. Model Registry records the dataset snapshot, the code, and the metrics. Promotion to a vehicle build requires a safety gate stored as an immutable record: evaluation against the bar, a human approval, and the regulatory region. EU personal data is not copied into a non-EU training project. Lineage in Dataplex is how that constraint is checked.</p>

<h2>6. Security and incident response</h2>
<p>Past breaches set the default. Staff and dealer tools sit behind IAP and Chrome Enterprise Premium. Production admin goes through Privileged Access Manager. VPC Service Controls enclose telemetry, BigQuery, Cloud Storage, and Vertex AI. CMEK covers those stores. Customer-managed encryption and confidential computing are used for autonomy model weights and for the restricted zone, not for every stateless web tier. Security Command Center Premium watches posture and attack paths. Google Security Operations is the detection platform, fed by organization log sinks with retention locks on the forensic bucket. Secrets live in Secret Manager. Service-account keys are forbidden by organization policy. Workload Identity is the identity for workloads.</p>
<p>The incident response plan names severity, the incident commander, legal and privacy contacts for EU data, and the decision rights for disabling a monetization listing or an OTA ring. The plan is drilled twice a year. Security awareness training is a required control for staff with production or data-zone access, tracked in the same operational review as patch SLOs. A tabletop that includes a dealer credential and a telemetry bucket is part of the first drill, because those are the paths a previous breach makes credible.</p>

<h2>7. Identity</h2>
<p>Drivers use Identity Platform, with the profile stored in the home-market region. Dealer staff use federation to the dealer directory. Employees use Workforce Identity Federation to the corporate directory. Vehicles use their own identity, distinct from the driver, so a sold car and a driver account can be unbound. OTA signing keys are in Cloud KMS with a narrow signer role used only by the release pipeline.</p>

<h2>8. Well-Architected alignment</h2>
{table(
    ["Pillar", "How this architecture applies it"],
    [
        ["Operational excellence", "Paved Terraform modules, one API gateway, an outbox the operations team can replay, Looker SLOs shared with commerce, and a platform team that is the upskilling path. Drills cover failover, OTA rollback, and incidents."],
        ["Security, privacy, and compliance", "Zero trust, perimeter, CMEK, PAM, Security Operations, consent enforced at ingest, EU Assured Workloads, and autonomy projects that cannot pull EU personal data sideways. Monetization has a zone and a contract."],
        ["Reliability", "The buyer’s commit does not wait on the mainframe. The car does not wait on the radio. Plants that need it keep a local cloud. Multi-zone Cloud SQL and a regional restore back the order record. Degraded modes are specified, not implied."],
        ["Cost optimization", "Browser dealers avoid a hardware program. Spot simulation covers restartable runs. Lifecycle policies cool sensor data. The ERP is wrapped, so the company does not fund a big-bang replacement to fix discounts. Committed use follows a measured baseline."],
        ["Performance optimization", "CDN for OTA and content packs, a read model for dealer screens, regional gateways for telemetry, and onboard inference where round-trip time is not acceptable. Shopper configuration is cached; the order insert is not."],
        ["Sustainability", "Sensor archives leave hot storage. GPU pools scale with campaigns. Spot and scheduled simulation avoid idle accelerators. In-region processing for EU vehicles avoids moving large telemetry across continents."],
    ],
)}

<h2>9. Service levels and disaster recovery</h2>
{table(
    ["Journey", "SLI", "Target", "When a dependency fails"],
    [
        ["Build-to-order commit", "Accepted orders that return a durable status", "99.9% monthly", "Cloud SQL regional failover. ERP lag is visible and is not a failed order."],
        ["Dealer portal", "Successful portal requests", "99.9% monthly", "Read model serves the last consistent snapshot."],
        ["OTA control plane", "Vehicles that can check in and receive a staged update", "99.9% monthly", "Previous package remains installed. Rollback is a stage, not a heroics script."],
        ["Telemetry durability", "Accepted messages once the vehicle has connected", "99.9% into Pub/Sub", "Vehicle buffer holds the priority queues while the link is down."],
        ["Driving safety", "Onboard behavior", "Outside the cloud SLO", "Cloud features are absent. The car remains in its safe onboard mode."],
        ["Regional restore", "Order record inside the market region", "RPO 15 minutes, RTO 4 hours", "PITR to the paired region in the same folder. EU does not restore into the US."],
    ],
)}

<h2>10. Cost and sustainability</h2>
<p>The program is meant to be funded in part by data products, so the architecture makes a product cheap to publish and expensive to do unsafely. Aggregates are the listing. Restricted-zone queries are not. Internally, predictive maintenance is the first return, because it uses telemetry the company already intends to collect for service. Sensor storage is the cost to watch: hot windows for active training, colder classes after, and lifecycle rules that do not depend on a person remembering. GPU spend is campaign-based. The Interconnect bill is tied to plants that have the traffic; smaller sites stay on HA VPN until measurement says otherwise.</p>

<h2>11. Roadmap</h2>
{table(
    ["Window", "Outcome"],
    [
        ["Year 1", "Organization, EU and US folders, perimeter, CMEK, identity, Security Operations, incident drills, Interconnect for HQ and the largest plants, Cloud SQL ordering, dealer portal read model, promotions service."],
        ["Year 2", "Apigee vehicle API, OTA with staged rollback, head-unit bridge for the highest-volume legacy models, telemetry gateway, Dataplex zones, predictive maintenance into the service module."],
        ["Year 3", "CRM and customer 360, consent enforcement at the gateway, Analytics Hub listings that legal has approved, GDC at the plants that fail closed today, ERP outbox extended beyond orders."],
        ["Years 4–5", "Autonomy project in the first cleared region: simulation, registry, safety gate. Further legacy head units. Dual Run only for mainframe workloads the assessment selects. External data products expand only from the monetizable zone."],
    ],
)}
<p>The five-year horizon in the case study is this sequence. Ordering reliability and the security foundation come first because dealer trust and the breach history are current harms. Autonomy at scale comes after the data platform can prove residency and lineage.</p>

<h2>12. Decision record</h2>
{table(
    ["Topic", "Choice", "Why this is the fit"],
    [
        ["Driving AI", "On the vehicle, with cloud features beside it", "Rural coverage and safety both forbid a cloud round-trip in the control loop."],
        ["Ingest", "Regional HTTPS and MQTT gateway to Pub/Sub", "Cloud IoT Core is retired. The gateway matches intermittent links and home-region data."],
        ["One UX", "Single API, capability flags, signed OTA, a legacy bridge", "Cohesion across BEV, hybrid, and ICE without pretending every head unit is new."],
        ["Orders", "Cloud SQL commit, ERP updated by outbox", "The shopper and the dealer get a durable answer while the systems of record stay in place."],
        ["Dealers", "Browser portal on Identity-Aware Proxy", "There is no dealer hardware budget. The tools have to run on today’s PCs."],
        ["ERP and mainframe", "Anti-corruption layer now, Dual Run only after assessment", "Promotions and orders are the pain. A full replacement is a different program."],
        ["Plants", "Interconnect, segmented OT, GDC where partitions hurt", "Headquarters connectivity and shop-floor survival are both required, and they are not the same design."],
        ["Monetization", "Dataplex zones and Analytics Hub aggregates", "Meets the funding aim and the privacy constraint at the same time."],
        ["AI platform", "Vertex AI, BigQuery, Feature Store", "Replaces obsolete AI infrastructure and scales training without standing it up in every plant."],
        ["Autonomy", "Isolated, cleared-region projects and a promotion gate", "Regulation is uneven, and a model that reaches a car needs a record."],
        ["Security", "Zero trust, SCC Premium, Security Operations, drills", "The company has already had the incident that a perimeter VPN would not have prevented."],
    ],
)}

<h2>13. Service map</h2>
{table(
    ["Concern", "Services"],
    [
        ["Vehicle and OTA", "Apigee, GKE gateway, Pub/Sub, Dataflow, Cloud Storage, Cloud CDN, Cloud KMS signing, Identity Platform"],
        ["Commerce and dealers", "Cloud Run, GKE, Cloud SQL, Memorystore, IAP, Looker, Global external Application Load Balancer, Cloud Armor"],
        ["Enterprise core", "Anti-corruption services on GKE, Workflows, Pub/Sub outbox, Dedicated or Partner Interconnect, HA VPN, Google Distributed Cloud"],
        ["Data and AI", "Dataplex, BigQuery, BigQuery ML, Vertex AI, Feature Store, Model Registry, Pipelines, Batch, Analytics Hub, Sensitive Data Protection"],
        ["Trust", "Assured Workloads, VPC Service Controls, Cloud KMS, Secret Manager, Privileged Access Manager, Workforce Identity Federation, Chrome Enterprise Premium, Security Command Center Premium, Google Security Operations, Access Transparency"],
        ["Delivery", "Cloud Build, Artifact Registry, Binary Authorization, Cloud Deploy, Infrastructure Manager, Cloud Monitoring, Cloud Logging, Cloud Trace"],
    ],
)}
<p class="small">This is a proposed target for design review against the published KnightMotives Automotive case study. It is not an official exam answer key. Plant circuits, consent copy, and the regions cleared for autonomy are confirmed with operations, privacy counsel, and regulators before those phases start.</p>
"""
    return {
        "slug": "knight",
        "filename": "KnightMotives_Automotive_Target_Architecture.pdf",
        "footer": "KnightMotives Automotive  ·  Target architecture  ·  PCA case study",
        "accent": ACCENT,
        "accent2": ACCENT2,
        "band": BAND,
        "cover": "linear-gradient(165deg, #020617 0%, #0f172a 52%, #1e293b 100%)",
        "kicker": "Professional Cloud Architect  ·  Case study architecture",
        "title": "KnightMotives Automotive",
        "subtitle": "Target architecture for one vehicle experience, reliable ordering, and governed data",
        "lede": "On-vehicle intelligence for the drive, a cloud order dealers can trust, a private path for plants, and a Vertex AI and Dataplex platform that keeps European data and autonomy inside the law.",
        "chips": [
            "Operational excellence",
            "Security, privacy, and compliance",
            "Reliability",
            "Cost optimization",
            "Performance optimization",
            "Sustainability",
        ],
        "meta": [
            ("Document", "Target architecture"),
            ("Date", "3 November 2026"),
            ("Framework", "Google Cloud Well-Architected Framework"),
            ("Status", "Proposal for design review"),
            ("Horizon", "Five years, phased"),
            ("Stays in place", "Mainframe, ERP, and dealer PCs"),
        ],
        "cover_points": [
            "Driving intelligence stays on the vehicle. The cloud engages, it does not steer.",
            "A build-to-order is committed in Cloud SQL, then posted to ERP.",
            "Dealers work in a browser on the PCs they already own.",
            "EU personal data and autonomy training stay inside cleared regions.",
        ],
        "cover_foot": "Aligned to the six pillars of the Google Cloud Well-Architected Framework. Service names as of November 2026.",
        "body": body,
    }
