"""EHR Healthcare target architecture."""

from render import banded, columns, figure, flow, note, table

ACCENT = "#0f4c5c"
ACCENT2 = "#b45309"
BAND = "#e7f0f2"


def document() -> dict:
    layers = banded(
        [
            (
                "People",
                [
                    ("Clinicians", "Web apps, per residency"),
                    ("Payers, new", "FHIR and HL7 through Apigee"),
                    ("Payers, legacy", "Files and APIs that stay on-premises"),
                    ("Operators", "Existing Active Directory"),
                ],
            ),
            (
                "Edge",
                [
                    ("Global App LB", "Nearest healthy residency"),
                    ("Cloud Armor", "WAF on clinical apps"),
                    ("Cloud CDN", "Static assets only"),
                    ("Workforce federation", "AD remains the directory"),
                ],
            ),
            (
                "Clinical apps",
                [
                    ("GKE Autopilot", "Regional, private, per residency"),
                    ("Cloud Deploy", "Canary across environments"),
                    ("Config Sync", "Same policy on every cluster"),
                    ("Project factory", "Stamp a new environment"),
                ],
            ),
            (
                "Clinical data",
                [
                    ("Cloud SQL", "MySQL and SQL Server, regional HA"),
                    ("Memorystore", "Redis-compatible cache"),
                    ("Firestore", "Document workloads from MongoDB"),
                    ("Healthcare API", "FHIR and HL7 for new payers"),
                ],
            ),
            (
                "Insight",
                [
                    ("De-identification", "Healthcare API and DLP"),
                    ("BigQuery", "Trends, per residency folder"),
                    ("BigQuery ML", "Predictions before custom training"),
                    ("Looker", "Reports leadership already expects"),
                ],
            ),
            (
                "Foundation",
                [
                    ("Assured Workloads", "US and EU residency folders"),
                    ("Interconnect", "To the colo that remains"),
                    ("VPC-SC + CMEK", "PHI perimeter and keys"),
                    ("Cloud Ops", "SLO paging, retained logs"),
                ],
            ),
        ],
        ACCENT,
        BAND,
    )
    hybrid = columns(
        [
            (
                "Colo that remains",
                [
                    "Legacy payer file and API systems",
                    "Active Directory",
                    "No migration in this program",
                    "Local operations stay local",
                ],
            ),
            (
                "Private connectivity",
                [
                    "Dedicated Interconnect, diverse",
                    "HA VPN backup",
                    "Cloud Router, BGP",
                    "DNS forwarding both ways",
                ],
            ),
            (
                "Google Cloud residency",
                [
                    "Shared VPC, private GKE",
                    "Apigee as the stable interface",
                    "Healthcare API for new payers",
                    "Cloud SQL in that residency only",
                ],
            ),
        ],
        ACCENT,
        BAND,
    )
    onboard = flow(
        [
            ("Contract", "Apigee product and sandbox project"),
            ("Ingest", "FHIR or HL7v2 into Healthcare API"),
            ("Process", "Pub/Sub and Dataflow, standard schema"),
            ("Record", "Regional clinical store"),
            ("Learn", "De-identified mart, then reports"),
        ],
        ACCENT,
    )
    release = flow(
        [
            ("Change", "Reviewed infra and app commit"),
            ("Attest", "Scan, sign, Binary Authorization"),
            ("Stage", "Environment stamped like production"),
            ("Canary", "Cloud Deploy, then full residency"),
        ],
        ACCENT,
    )

    body = f"""
<h2>1. Purpose</h2>
<p>This paper is the target architecture for EHR Healthcare. EHR sells electronic health record software as a service to clinics, hospitals, and insurers in more than one country. Growth is outrunning a set of colocation facilities. One facility’s lease is ending. Customer-facing applications are web applications, many of them already containerized on Kubernetes. Data is in MySQL, SQL Server, Redis, and MongoDB. Integrations with insurers include legacy file and API systems that stay on-premises for years. Users are in Microsoft Active Directory. Open-source monitoring sends email that operators ignore. Outages have come from misconfiguration, from capacity that could not take a spike, and from monitoring that differed by site.</p>
<p>Google Cloud replaces the expiring colocation facility and becomes the pattern for the rest. The design commits to 99.9% availability for customer-facing systems, lower latency for an international customer base, faster onboarding of new insurers, regulatory compliance, and less infrastructure administration. It does this with residency-scoped data planes, one delivery system, and a hybrid link to the interfaces that are not moving.</p>

<h2>2. Assumptions</h2>
{table(
    ["Topic", "Assumption used in this design"],
    [
        ["Residency", "Clinical data for a customer stays in the residency that contract and law require. The reference residencies are a US folder and an EU folder. A further country is another folder, not a table in the US database."],
        ["Availability", "99.9% is measured at the load balancer for customer-facing systems, monthly. That budget is about 43 minutes. It is met with regional multi-zone services and a tested restore, not with active-active writes of the same record across continents."],
        ["Legacy payers", "File and API integrations in the remaining colo are frozen in place. New payers do not use that pattern."],
        ["Tenancy", "Most customers share a residency’s application and database, isolated by tenant key and IAM. A customer who requires stronger isolation receives a stamped project."],
        ["Agreement", "A Business Associate Agreement, or the local equivalent, is in place before PHI moves. Assured Workloads folders are the control."],
    ],
)}

<h2>3. Requirements and the response</h2>
{table(
    ["ID", "Requirement", "Architecture response"],
    [
        ["BR1", "On-board insurers quickly", "Apigee product, sandbox from the project factory, Healthcare API ingest, one pipeline."],
        ["BR2", "99.9% for customer-facing systems", "Regional GKE, regional Cloud SQL HA, global LB health checks, error-budget policy."],
        ["BR3", "Central visibility and proactive action", "Org log sinks, SLO burn alerts to an incident channel, uptime checks, Trace."],
        ["BR4", "Insights on healthcare trends", "De-identified BigQuery marts and Looker, per residency."],
        ["BR5", "Lower latency for all customers", "Global anycast LB to the residency region nearest the user, CDN for static assets."],
        ["BR6", "Regulatory compliance", "Assured Workloads, CMEK, VPC Service Controls, audit logs, BAA, no PHI in analytics."],
        ["BR7", "Less infrastructure administration", "Autopilot, managed databases, fleet policy, one observability stack."],
        ["BR8", "Predictions and reports from provider data", "BigQuery ML on the de-identified mart. Vertex AI only if a model outgrows SQL ML."],
        ["TR1", "Keep legacy insurer interfaces, reachable from cloud and colo", "Dedicated Interconnect and Apigee. The legacy systems stay."],
        ["TR2", "Consistent management of container apps", "GKE Enterprise fleet, Config Sync, Policy Controller, Cloud Deploy."],
        ["TR3", "Secure, high-performance on-premises link", "Diverse Dedicated Interconnect, HA VPN backup, private DNS."],
        ["TR4", "Consistent logging, retention, monitoring, alerting", "Aggregated sinks, retention locks where policy requires, one alert standard."],
        ["TR5", "Multiple container environments", "Separate clusters and projects for dev, test, staging, and each production residency."],
        ["TR6", "Dynamically scale and provision environments", "Terraform modules through Infrastructure Manager, plus HPA and node autoscaling."],
        ["TR7", "Interfaces to ingest and process new providers", "Cloud Healthcare API, Pub/Sub schemas, Dataflow. A new payer is configuration."],
    ],
    id_col=True,
)}

<h2>4. Current state</h2>
<p>Containers are already the right shape for the customer-facing tier, and they are spread across facilities the company must stop running by hand. The lease expiry sets the first move: that facility’s clusters and databases go to Google Cloud, customer by customer, residency by residency. The other facilities follow the same module. What does not move is the legacy insurer connectivity the case study keeps on-premises for years. Active Directory stays the workforce directory. Email-only alerts are treated as a design defect, because they are already how incidents are missed.</p>

<h2>5. Target architecture</h2>
<p>Each residency is a folder under Assured Workloads with its own Shared VPC service projects, its own GKE clusters, and its own databases. A global external Application Load Balancer sends a user to the healthy backend in that user’s residency. It does not replicate the clinical record to a second continent to shave latency. Latency is solved by placing the residency close to those users, and by caching only static assets on Cloud CDN. Clinical responses are not cached.</p>
{figure(layers, "Figure 1. EHR target architecture. Insight is downstream of de-identification. Legacy payers stay on the private link.")}
{note("<strong>Availability arithmetic.</strong> Multi-zone regional GKE and regional Cloud SQL meet 99.9% inside one residency. A second region inside the same residency folder is the disaster-recovery site. It is warm for the application and restored for the database. It is not a second writer for the same row.")}

<h3>The link that lets legacy systems stay</h3>
{figure(hybrid, "Figure 2. The colo that remains is part of the production network, and it is not a migration backlog.")}
<p>Dedicated Interconnect, two links in different availability domains, with Cloud Router and BGP, carries clinical traffic between the remaining colo and each residency’s Shared VPC through a controlled hub. HA VPN is the tested backup. Cloud DNS forwarding resolves names in both directions. Private Google Access and Private Service Connect keep Google API calls off the public internet.</p>
<p>Apigee is the stable interface. Cloud services call legacy payer APIs through Apigee, and legacy systems call cloud services the same way, with quotas and credentials that can be rotated without opening a firewall ticket per address. File drops that must continue land on storage in the colo and are pulled across Interconnect into a quarantined bucket by Storage Transfer Service, then parsed by Dataflow. New work does not add new file drops.</p>

<h3>A new insurer</h3>
{figure(onboard, "Figure 3. Onboarding is a product and a pipeline, not a project plan per payer.")}
<p>Cloud Healthcare API is the ingestion front for new providers: FHIR for modern interfaces, HL7v2 where that is still the wire format. Apigee exposes the product, and the project factory stamps a sandbox project with the same policies as production and none of the production data. Messages land on Pub/Sub with a schema, Dataflow maps them into the residency’s clinical store, and failures are dead-lettered with the payer id. Adding a payer is a new Apigee app, a schema mapping, and a contract test. The legacy colo interface is not copied forward.</p>

<h3>Clinical data</h3>
<p>MySQL moves to Cloud SQL for MySQL and SQL Server moves to Cloud SQL for SQL Server, each regional, highly available, with PITR, private IP, and CMEK. Database Migration Service runs the replication. Redis moves to Memorystore. MongoDB-shaped data moves to Firestore. A dataset that later needs strong consistency across two regions inside the same residency can move to Spanner as a deliberate follow-on. It is not the default, because the engines in place today have a direct managed landing zone and the residency boundary already limits the blast radius.</p>
<p>Tenant isolation for the shared tier is a tenant key on every row, IAM that application code cannot bypass for exports, and database users that are not shared across services. The isolated tier, vended by the same Terraform module, is a project, a cluster namespace or a dedicated cluster, and a dedicated Cloud SQL instance for the customers whose contracts require it. That is the dynamic environment the technical requirement describes, and it is also how dev, test, and staging stay shaped like production.</p>

<h3>Change, which is how the outages started</h3>
{figure(release, "Figure 4. A production change is attested and canaried. Console drift is not a path.")}
<p>Infrastructure Manager applies reviewed Terraform. Config Sync and Policy Controller reject a cluster that drifts from the fleet standard. Cloud Build produces images, Artifact Registry scans them, and Binary Authorization is required in every production residency. Cloud Deploy canaries the application. Pod disruption budgets and horizontal pod autoscaling, fed by request rate and not only by CPU, are part of the template so a traffic spike scales out before it becomes the outage the executive statement describes. Load tests run against staging as a pipeline gate for changes that touch the request path.</p>

<h2>6. Insight without moving PHI into the warehouse</h2>
<p>Trends and predictions are a product, and they are downstream of de-identification. Cloud Healthcare API de-identification and Sensitive Data Protection produce the dataset that BigQuery is allowed to hold, still inside the residency folder. BigQuery ML builds the forecasts and classifications leadership asked for. Looker is the report layer, with row-level restrictions by customer where a hospital sees only its own trends and EHR staff see the consented industry view. Vertex AI is reserved for a model that BigQuery ML cannot express. Identified production databases are not the analytics source, and analysts do not hold production IAM.</p>

<h2>7. Identity and operations</h2>
<p>Workforce Identity Federation trusts the existing Active Directory, via the organization’s Entra ID or AD FS. Operators do not get a second password. Privileged Access Manager grants time-bound production access. Break-glass is a logged group, not a shared key. Application workloads use Workload Identity.</p>
<p>Logging is an organization sink into a residency-scoped log bucket, with the retention the compliance schedule names and with exclusion filters so PHI does not land in debug logs. Application logs carry a tenant id and a request id, not a clinical note. Cloud Monitoring uptime checks run from more than one region. Alerting uses multi-window burn rates on the SLOs below and opens an incident. Email is a subscription for people who want a copy, and it is not the paging path. The weekly operations review includes alerts that fired and were not actionable, so the signal stays small enough to trust.</p>

<h2>8. Well-Architected alignment</h2>
{table(
    ["Pillar", "How this architecture applies it"],
    [
        ["Operational excellence", "Project factory, Git-reviewed change, canaries, SLO paging, and a single fleet standard. This is the direct response to misconfiguration and inconsistent monitoring. Capacity is an autoscaler and a load-test gate, not a ticket after the spike."],
        ["Security, privacy, and compliance", "Assured Workloads, CMEK, VPC Service Controls, private SQL, Healthcare API for the clinical interface, de-identification before analytics, workforce federation to AD, and Privileged Access Manager. PHI stays in the residency folder."],
        ["Reliability", "99.9% is an explicit user-facing target with an error budget. Multi-zone redundancy, health-checked global entry, graceful behavior when the legacy colo is unreachable, backups, and a restore test. Postmortems are part of the operating rhythm because the pillar asks for them."],
        ["Cost optimization", "Autopilot and managed databases remove colo and guest administration. Isolated projects are used when a contract requires them, not for every clinic. Analytics uses BigQuery ML before a training platform. CDN caches static assets only."],
        ["Performance optimization", "Users terminate in their residency. Cloud SQL connection pooling and Memorystore protect the database. Healthcare API and Dataflow scale ingest with the payer, not with a hand-sized VM."],
        ["Sustainability", "Regions are chosen for residency first, and the DR region is the paired region inside that same boundary so data is not copied worldwide. Autoscaling tracks clinic hours. Batch prediction runs on the de-identified mart in-region."],
    ],
)}

<h2>9. Service levels and disaster recovery</h2>
{table(
    ["Journey", "SLI", "Target", "When a dependency fails"],
    [
        ["Clinical app", "Successful requests at the load balancer, excluding caller errors", "99.9% monthly", "Multi-zone failover inside the region. Static assets remain on CDN."],
        ["Interactive latency", "p95 time to first byte for the residency’s users", "Budget set per residency in discovery", "Users are not routed to another residency’s database."],
        ["New-payer ingest", "Messages accepted into Healthcare API", "99.9% monthly", "Pub/Sub absorbs a downstream stall. Legacy colo files are a separate interface."],
        ["Legacy colo call", "Successful calls through Apigee to systems that remain", "Best effort of that system", "Clinical app shows a clear deferred state. It does not block unrelated charting."],
        ["Restore", "RPO and RTO inside the residency", "RPO 15 minutes, RTO 4 hours", "PITR and a warm application in the paired region. Restore is rehearsed."],
    ],
)}
<p>Backups are Cloud SQL automated backups and PITR, Firestore exports, and Backup for GKE. A residency disaster restores into the paired region inside the same Assured Workloads folder. Cross-border restore is not a feature. The error-budget policy slows releases when the monthly budget is spent, which is how reliability stays a product decision.</p>

<h2>10. Security and compliance</h2>
<p>The perimeter covers Cloud SQL, Firestore, Healthcare API, BigQuery, and Cloud Storage in the residency. Support access from Google is covered by Access Transparency, with Access Approval on the production folders. Customer-managed encryption keys are held in Cloud KMS in the same residency. External payer credentials live in Secret Manager and rotate. VPC firewall rules and hierarchical firewall policies default to deny. The only public surface is the load balancer, protected by Cloud Armor.</p>
<p>Compliance evidence is the folder configuration, the audit logs, the de-identification step, and the residency of the backups. It is not a slide. A new country is a new folder and a new key ring, stamped by the same module.</p>

<h2>11. Cost and sustainability</h2>
<p>Administration cost is the line the executive statement is already paying for: people who keep similar environments different. The factory and the fleet make the next environment cheap to create and cheap to keep aligned. Colo cost falls as each lease ends and the workloads are already on the module. Interconnect is sized for the legacy traffic that remains, and it is reviewed when those interfaces are finally retired years from now. Rightsizing uses Recommender and the billing export, with committed use purchased against the steady production baseline after a quarter, per residency.</p>

<h2>12. Roadmap</h2>
{table(
    ["Phase", "Outcome"],
    [
        ["1. Foundation", "Organization, Assured Workloads folders, Shared VPC, perimeter, CMEK, log sinks, Interconnect and HA VPN, identity federation, paging."],
        ["2. Expiring site", "Database Migration Service and GKE migration for the facility whose lease is ending, one residency at a time, customers in waves."],
        ["3. Delivery system", "Project factory, Config Sync, Binary Authorization, Cloud Deploy canaries, load-test gate, backup restore rehearsal."],
        ["4. New payers", "Healthcare API, Apigee, Pub/Sub, Dataflow. Sandbox stamping. Legacy file path only where a current contract forces it."],
        ["5. Insight", "De-identification, BigQuery, BigQuery ML, Looker. Vertex AI only on evidence that SQL ML is insufficient."],
        ["6. Remaining sites", "Each later colo moves on the same module. Legacy payer systems stay, reached over Interconnect, until their own retirement years out."],
    ],
)}

<h2>13. Decision record</h2>
{table(
    ["Topic", "Choice", "Why this is the fit"],
    [
        ["First move", "The colo whose lease is ending", "That is the deadline. The module is what later sites reuse."],
        ["Legacy payers", "Leave them, connect them", "The case study keeps these systems on-premises. Apigee and Interconnect make that a design, not a gap."],
        ["New payers", "Cloud Healthcare API and a standard pipeline", "Onboarding speed comes from one interface, not from cloning file transfer."],
        ["Records", "Cloud SQL, Memorystore, Firestore", "Direct managed targets for MySQL, SQL Server, Redis, and MongoDB, private and regional."],
        ["Global users", "Anycast entry, data stays in residency", "Latency improves because the stack is near the user. The record is not smeared across legal boundaries."],
        ["99.9%", "Multi-zone regional HA and a 4-hour regional restore", "Matches the stated target and the error budget. A worldwide active-active record is a different requirement."],
        ["Many environments", "GKE Enterprise fleet and a project factory", "Consistency is the cure for the misconfigurations leadership described."],
        ["Signals", "SLO burn to an incident channel, retained sinks", "Email already failed as the alerting design."],
        ["Predictions", "BigQuery ML on de-identified data", "Meets the insight requirement without a second copy of PHI and without a premature training platform."],
        ["Workforce", "Federation to existing Active Directory", "AD stays the directory the company already runs."],
    ],
)}

<h2>14. Service map</h2>
{table(
    ["Concern", "Services"],
    [
        ["Entry", "Global external Application Load Balancer, Cloud Armor, Cloud CDN, Cloud DNS"],
        ["Compute", "GKE Autopilot, GKE Enterprise, Config Sync, Policy Controller, Cloud Build, Artifact Registry, Binary Authorization, Cloud Deploy, Infrastructure Manager"],
        ["Data", "Cloud SQL for MySQL, Cloud SQL for SQL Server, Memorystore, Firestore, Database Migration Service, Backup for GKE"],
        ["Payers", "Cloud Healthcare API, Apigee, Pub/Sub, Dataflow, Storage Transfer Service"],
        ["Insight", "Sensitive Data Protection, BigQuery, BigQuery ML, Looker, Dataplex"],
        ["Trust", "Assured Workloads, VPC Service Controls, Cloud KMS, Secret Manager, Privileged Access Manager, Workforce Identity Federation, Access Transparency, Access Approval, Security Command Center"],
        ["Network", "Shared VPC, Cloud Router, Dedicated Interconnect, HA VPN, Private Service Connect"],
        ["Operations", "Cloud Logging, Cloud Monitoring, Cloud Trace, Error Reporting"],
    ],
)}
<p class="small">This is a proposed target for design review against the published EHR Healthcare case study. It is not an official exam answer key. Residency regions, RPO, and retention are confirmed with counsel and with the Business Associate Agreement before PHI moves.</p>
"""
    return {
        "slug": "ehr",
        "filename": "EHR_Healthcare_Target_Architecture.pdf",
        "footer": "EHR Healthcare  ·  Target architecture  ·  PCA case study",
        "accent": ACCENT,
        "accent2": ACCENT2,
        "band": BAND,
        "cover": "linear-gradient(165deg, #07262c 0%, #0f4c5c 58%, #12343c 100%)",
        "kicker": "Professional Cloud Architect  ·  Case study architecture",
        "title": "EHR Healthcare",
        "subtitle": "Target architecture for a compliant, multi-residency clinical platform",
        "lede": "Regional clinical stacks on GKE and Cloud SQL, a private link to the insurer systems that stay behind, and one delivery and paging model so the next environment matches the last.",
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
            ("Availability", "99.9% customer-facing, measured monthly"),
            ("Stays on-premises", "Legacy insurer file and API systems"),
        ],
        "cover_points": [
            "Each residency keeps its own clinical data plane.",
            "99.9% is met with multi-zone services and a paired-region restore.",
            "Legacy insurer systems stay in the remaining colo, reached over Interconnect.",
            "New insurers onboard through Cloud Healthcare API, Apigee, and one pipeline.",
        ],
        "cover_foot": "Aligned to the six pillars of the Google Cloud Well-Architected Framework. Service names as of November 2026.",
        "body": body,
    }
