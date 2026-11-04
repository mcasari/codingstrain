"""Cymbal Retail target architecture."""

from render import banded, columns, figure, flow, note, table

ACCENT = "#9f1239"
ACCENT2 = "#0f766e"
BAND = "#f8efe8"


def document() -> dict:
    layers = banded(
        [
            (
                "People",
                [
                    ("Shoppers", "Web and mobile"),
                    ("Associates", "Catalog review desk"),
                    ("Suppliers", "Files today, APIs next"),
                    ("Agents", "Calls the bot could not finish"),
                ],
            ),
            (
                "Edge",
                [
                    ("CDN + App LB", "Images, storefront, WAF"),
                    ("Cloud Armor", "Bot and abuse controls"),
                    ("Identity Platform", "Shopper accounts"),
                    ("IAP", "Associate review desk"),
                ],
            ),
            (
                "Commerce",
                [
                    ("GKE Autopilot", "Storefront and orders API"),
                    ("AI Commerce Search", "Search, rank, recommendations"),
                    ("Conversational agent", "Guided discovery and order tools"),
                    ("Cloud Run", "Review desk and supplier glue"),
                ],
            ),
            (
                "Enrichment",
                [
                    ("Workflows", "Parse, generate, validate, queue"),
                    ("Gemini", "Attributes bound to the taxonomy"),
                    ("Imagen", "Color, background, overlay"),
                    ("Human review", "Approve, edit, or reject"),
                ],
            ),
            (
                "Systems of record",
                [
                    ("Cloud SQL", "Catalog, price, stock, orders"),
                    ("Firestore", "Proposals and document-shaped content"),
                    ("Memorystore", "Sessions and hot catalog keys"),
                    ("BigQuery", "Journey, search quality, CDC"),
                ],
            ),
            (
                "Foundation",
                [
                    ("Apigee", "Suppliers, payments, partners"),
                    ("DMS + Datastream", "Migration and ongoing CDC"),
                    ("Shared VPC + VPC-SC", "Private data plane"),
                    ("Cloud Ops", "SLOs instead of three consoles"),
                ],
            ),
        ],
        ACCENT,
        BAND,
    )
    enrich = flow(
        [
            ("Intake", "API or SFTP lands the supplier pack"),
            ("Generate", "Schema-bound attributes and images"),
            ("Validate", "Taxonomy check, then the queue"),
            ("Review", "Associate edits or approves"),
            ("Publish", "Catalog, search index, and CDN"),
        ],
        ACCENT,
    )
    shop = flow(
        [
            ("Ask", "Natural language on web or mobile"),
            ("Search", "AI Commerce Search over the live catalog"),
            ("Confirm", "Price and stock from the orders API"),
            ("Buy", "Same orders API the call center uses"),
        ],
        ACCENT,
    )
    integrate = columns(
        [
            (
                "Partners who can change",
                [
                    "HTTPS products on Apigee",
                    "OAuth and quotas",
                    "Catalog and order APIs",
                    "Payment token, never the PAN",
                ],
            ),
            (
                "Partners who cannot yet",
                [
                    "Private SFTP front end",
                    "Landing bucket and scanner",
                    "Dead-letter and replay",
                    "Dated exit for each feed",
                ],
            ),
            (
                "Inside Cymbal",
                [
                    "Cloud SQL is the record",
                    "Search index is a projection",
                    "BigQuery is the journey",
                    "One remediation console",
                ],
            ),
        ],
        ACCENT,
        BAND,
    )

    body = f"""
<h2>1. Purpose</h2>
<p>This paper is the target architecture for Cymbal Retail. Cymbal is a fast-growing multi-category retailer with a catalog that is already hard to manage, a custom web application that searches MySQL and SQL Server by name and category, supplier feeds over SFTP and nightly batch ETL, an IVR and a call center that re-keys orders, and Kubernetes beside a mix of on-premises systems. Redis and MongoDB sit in the same estate. Monitoring is split across Grafana, Nagios, and Elastic.</p>
<p>The program has three outcomes: catalog and image enrichment with a person in the loop, conversational discovery on Google Cloud’s commerce search, and a smaller, more operable technical stack. Search, stock, and payment stay separate so a model can recommend a product and cannot invent a price or see a card number.</p>
<p class="small">The case study asks for Discovery AI. As of November 2026 that capability is AI Commerce Search in Gemini Enterprise for Customer Experience, previously Vertex AI Search for commerce and Retail Search. The Conversational Commerce agent is the shopper-facing guide on that index. This paper uses those current names.</p>

<h2>2. Assumptions</h2>
{table(
    ["Topic", "Assumption used in this design"],
    [
        ["Catalog record", "Price, stock, and order state stay relational. Generated copy and images are proposals until an associate publishes them."],
        ["Market", "One primary region serves the storefront. A second region in the same market is the warm standby. The pair is chosen in discovery."],
        ["Payments", "A payment processor tokenizes cards. Cymbal systems store a token and an order id. This estate is not a cardholder-data environment."],
        ["Suppliers", "Some suppliers will keep sending files for a defined period. New suppliers use the API."],
        ["Taxonomy", "Cymbal already has a category and attribute structure. Generation must emit that structure. Free-form attributes are defects, not copy."],
    ],
)}

<h2>3. Requirements and the response</h2>
{table(
    ["ID", "Requirement", "Architecture response"],
    [
        ["BR1", "Automate catalog enrichment and cut errors", "Workflow plus schema-bound generation, validation, and publish of only approved rows."],
        ["BR2", "Better product discovery", "AI Commerce Search replaces SQL name-and-category search."],
        ["BR3", "More engaging shopping, fewer returns", "Conversational guidance grounded on live attributes, with accurate size and material data from the reviewed catalog."],
        ["BR4", "Higher conversion", "Search ranking and the conversational agent share one index and one price-and-stock API."],
        ["BR5", "Lower call-center and data-center cost", "Agent completes simple orders. Managed databases and Autopilot replace hosted engines and guest OS toil. IVR hands residual calls to Agent Assist."],
        ["TR1", "Attributes from titles, descriptions, and images, aligned to category", "Gemini multimodal output validated against the category JSON schema before a human sees it."],
        ["TR2", "Image variants, backgrounds, color, text overlays", "Imagen for the pixels. Overlays come from an approved template list. Public CDN only after approval."],
        ["TR3", "Natural-language discovery", "Conversational Commerce agent on AI Commerce Search."],
        ["TR4", "Scale of catalog and growth", "Managed search, CDN for images, Autopilot for the storefront, Cloud SQL pooling for the record."],
        ["TR5", "Human review UI", "IAP-protected Cloud Run desk: approve, edit, reject, with the edit kept as the label."],
        ["TR6", "Security and compliance of customer and agent data", "Perimeter, CMEK, DLP on transcripts, tokenized payments, restricted audit dataset."],
    ],
    id_col=True,
)}

<h2>4. Current state</h2>
<p>The catalog is fragmented across MySQL, SQL Server, and MongoDB, and the shopper’s search path is a relational query. That path will not carry natural language or a growing assortment. File drops and batch ETL fail quietly and are repaired by hand. The call center types orders that the shopper already tried to place, because the IVR and the website do not share an orders API. Grafana, Nagios, and Elastic each see a slice of the estate, so nobody has one symptom-level view. Kubernetes is already present, which means the storefront move is a re-platform of deployment and data, not a rewrite into virtual machines.</p>

<h2>5. Target architecture</h2>
<p>Four planes keep the retail system honest. The <strong>record</strong> is Cloud SQL: product identity, price, stock, and orders. The <strong>index</strong> is AI Commerce Search, rebuilt from published catalog rows. The <strong>proposal</strong> plane holds generative copy and images until an associate accepts them. The <strong>journey</strong> is BigQuery, fed by Datastream and by search logs, and it is never allowed to write price back into the record.</p>
{figure(layers, "Figure 1. Cymbal target architecture. Search and generation sit beside the catalog, and they publish only through the record.")}
{note("<strong>Publish rule.</strong> A generated attribute or image becomes shopper-visible only after the review desk writes it to Cloud SQL and the catalog import updates AI Commerce Search. The model has no write credential on the live catalog.")}

<h3>Enrichment</h3>
{figure(enrich, "Figure 2. Supplier pack to live catalog. Invalid model output never looks like a ready suggestion.")}
<p>Supplier JSON and images enter through Apigee, or through the temporary SFTP front end. A Workflow extracts text, including Document AI when the pack is a PDF or a sheet. Gemini receives the category’s attribute schema and must return JSON that validates. Rows that fail validation go to an error queue for a merchandiser, not to the approval queue dressed as suggestions. Rows that validate land as proposals in Firestore, linked to the supplier’s source hash.</p>
<p>Imagen produces color variants, background replacements, and overlays. Overlay text is chosen from templates the brand team has already approved. Generated binaries stay in a private bucket. The review desk, a Cloud Run UI behind Identity-Aware Proxy, shows the proposal beside the supplier’s original. Associates approve, edit, or reject. An edit is stored as the accepted value and as a training-quality label for later evaluation. Approval copies the image to the public bucket, writes the attributes to Cloud SQL, and incrementally imports the product into AI Commerce Search. CDN cache is invalidated for that product’s image path.</p>
<p>Provenance on every published field includes model id, prompt-template version, source hash, and the associate’s identity. That is the audit trail for “who said this material was cotton.”</p>

<h3>Discovery and the order</h3>
{figure(shop, "Figure 3. Conversation can recommend. Price, stock, and payment are tool calls.")}
<p>The web and mobile clients send shopper language to the Conversational Commerce agent. The agent retrieves from AI Commerce Search, which is tuned for retail ranking and carries the recommendations use case that used to be a separate retail model. When the shopper asks for price, availability, or an order, the agent calls Cymbal’s APIs. Those APIs read Cloud SQL. The agent is not permitted to answer price or stock from the language model’s own text.</p>
<p>Checkout uses the same Orders API as the contact center. Simple orders finish in the conversation. The IVR front door moves to Customer Engagement Suite: the virtual agent handles status and straightforward placement; remaining calls reach a person with Agent Assist, which shows the same product and order context. Agents stop re-keying a second order. Payment is a redirect or a processor token. Transcripts go to a restricted dataset, pass through Sensitive Data Protection, and expire on the retention schedule.</p>

<h3>Integrations and the end of silent batch</h3>
{figure(integrate, "Figure 4. Two partner paths, one internal record.")}
<p>Apigee is the front door for suppliers, marketplaces, and the payment processor: quotas, OAuth, and a developer portal so a new supplier is a product subscription rather than a new batch job. Suppliers who cannot leave SFTP yet land files on a private GKE SFTP service that writes the ingest bucket. A scanner runs before the workflow. Each feed has an owner and a retirement date.</p>
<p>Dataflow replaces the nightly ETL. Failures go to a dead-letter topic and a remediation view where an operator can repair and replay. That is the “error handling and remediation” the solution concept asks for, and it is observable, unlike a failed cron email.</p>

<h2>6. Data migration and the customer journey</h2>
{table(
    ["Today", "Target", "Move"],
    [
        ["MySQL catalog and orders", "Cloud SQL for MySQL, HA, PITR", "Database Migration Service, then cutover"],
        ["SQL Server", "Cloud SQL for SQL Server, HA, PITR", "Database Migration Service, then cutover"],
        ["Redis", "Memorystore for Redis", "Dual-write or import, then cut the client"],
        ["MongoDB product documents", "Firestore", "Offline copy, then dual-write for the collections still in use"],
        ["Search on the relational engines", "AI Commerce Search", "Built from published Cloud SQL rows. SQL stops serving shopper search."],
        ["Siloed operational data", "BigQuery via Datastream", "CDC from Cloud SQL. This is the unified journey view."],
    ],
)}
<p>Cloud SQL uses regional high availability and managed connection pooling so the storefront’s pod count does not exhaust database connections. Read replicas serve internal merchandising queries. They do not serve shopper search. BigQuery tables are partitioned by event date and clustered by product and customer key. Looker is the semantic layer for conversion, return rate, search abandonment, and enrichment throughput.</p>
<p>The storefront itself runs on GKE Autopilot, private cluster, in the primary region, with a warm standby in the second region that can take traffic from the global load balancer. Images are cached on Cloud CDN. Product pages that must be fresh for price call the API; they are not fully cached HTML of a price.</p>

<h2>7. Identity, isolation, and operations</h2>
<p>Shoppers use Identity Platform. Associates and agents use Workforce Identity Federation, and the review desk is additionally behind IAP. Workloads use Workload Identity. The payment processor’s credentials and the commerce-search credentials live in Secret Manager.</p>
<p>Projects split storefront, catalog data, search, and security. The data and AI projects sit in a VPC Service Controls perimeter. Organization policies block service-account keys and public buckets except the published image bucket, which is readable only through the CDN and signed where the object is not meant to be world-readable.</p>
<p>Observability is a migration, not a weekend cutover. Managed Service for Prometheus keeps the Grafana dashboards that are still useful. Nagios checks become uptime checks and SLO burn alerts. Elastic indexes expire as Cloud Logging aggregated sinks take the retention role. Paging goes to the incident channel. The SLOs below are the only alerts that page.</p>

<h2>8. Well-Architected alignment</h2>
{table(
    ["Pillar", "How this architecture applies it"],
    [
        ["Operational excellence", "One orders API, replayable feeds, Cloud Deploy, and SLO paging. The review desk is an operational control, not a side process. Observability collapses onto Cloud Operations without throwing away Grafana on day one."],
        ["Security, privacy, and compliance", "Perimeter, CMEK, DLP on chats, tokenized payment, IAP for staff tools, and no model credential on the live catalog. Generated images are labeled in metadata and withheld until approval."],
        ["Reliability", "Managed search and CDN carry discovery. Cloud SQL HA carries the record. The agent degrades to ordinary search if generation is unavailable, because the index still answers. File feeds queue and replay."],
        ["Cost optimization", "Managed search avoids a custom retrieval cluster. Autopilot and Cloud SQL remove data-center host and database administration. The agent handles the calls that are mostly status and simple orders. Enrichment is event-driven, not a standing GPU pool."],
        ["Performance optimization", "Shopper search is the managed retail ranker, with CDN on images and Memorystore on hot keys. Generation is asynchronous and off the browse path. Connection pooling protects Cloud SQL."],
        ["Sustainability", "Autoscale the storefront, generate images only for products in the intake queue, and keep search on a managed service rather than idle GPU capacity. Batch replay of feeds runs in the same region as Cloud SQL."],
    ],
)}

<h2>9. Service levels and disaster recovery</h2>
{table(
    ["Journey", "SLI", "Target", "When a dependency fails"],
    [
        ["Browse and search", "Successful search responses within the latency budget", "99.9% monthly, p95 server time under 300 ms", "Cached images still render. Search is multi-zone managed."],
        ["Checkout", "Orders accepted by the Orders API", "99.95% monthly", "Regional Cloud SQL failover. The agent cannot take payment by chat text."],
        ["Enrichment", "Valid proposals visible on the desk", "95% of packs within one business hour", "Queue. The live catalog is unchanged."],
        ["Publish", "Approved item present in search", "95% within 15 minutes of approval", "Retry the import. The SQL write is the commit."],
        ["Contact deflection", "Share of contained contacts that end in the bot or in a completed order", "Measured, target set after baseline", "Overflow to people with Agent Assist. No second database."],
    ],
)}
<p>Recovery point on Cloud SQL is point-in-time, within the PITR window, with a target of 15 minutes of data for a regional disaster restored in the standby region inside four hours. Search is rebuilt from Cloud SQL if an index is corrupted, which is why SQL remains the record. Firestore proposals are export-backed daily; a lost proposal can be regenerated from the supplier pack hash.</p>

<h2>10. Security, privacy, and the review obligation</h2>
<p>Customer data and agent transcripts are customer data. They stay in the perimeter. Sensitive Data Protection inspects transcripts and enrichment text for data that does not belong in a product description, including account numbers a supplier should never have sent. Staff access to transcripts is a separate IAM role with a shorter list than catalog editors.</p>
<p>The human-in-the-loop requirement is implemented as a state machine, not a shared folder. States are received, rejected-by-schema, awaiting review, approved, and published. Every transition is an audit event. Brand and legal can sample published AI-assisted images because the metadata says the image was generated and which template supplied the overlay.</p>

<h2>11. Cost and sustainability</h2>
<p>Hosting cost falls as MySQL, SQL Server, Redis, MongoDB, and the batch servers leave the data center, on Database Migration Service timelines rather than a flag day. Call-center cost falls only after the Orders API and the agent share a path, so the roadmap puts that API before the IVR cutover. Generative cost is per intake item, which matches supplier arrivals, and failed schema checks do not call Imagen. Billing export to BigQuery splits search, generation, database, and egress so merchandising volume can be matched to the bill.</p>

<h2>12. Roadmap</h2>
{table(
    ["Phase", "Outcome"],
    [
        ["1. Foundation", "Folders, Shared VPC, perimeter, CMEK, Artifact Registry, Cloud Deploy, log sinks, paging on a single storefront SLO."],
        ["2. Record", "Database Migration Service into Cloud SQL and Memorystore. Firestore for the Mongo-shaped collections. Datastream into BigQuery."],
        ["3. Index", "AI Commerce Search on the published catalog. Storefront search cut over. SQL search retired for shoppers."],
        ["4. Enrichment", "Schema-bound Gemini, Imagen, review desk, provenance. SFTP adapter for the feeds that remain."],
        ["5. Conversation", "Conversational agent and Orders API, then Customer Engagement Suite in front of the IVR, with Agent Assist for the rest."],
        ["6. Exit", "Data-center databases and Nagios decommissioned. Each SFTP feed closed on its retirement date. Elastic retention overlap ends."],
    ],
)}

<h2>13. Decision record</h2>
{table(
    ["Topic", "Choice", "Why this is the fit"],
    [
        ["Discovery", "AI Commerce Search and the Conversational Commerce agent", "This is the managed retail interpretation of the case study’s Discovery AI requirement, including rank and recommendations."],
        ["Catalog writes", "Associates publish into Cloud SQL; search is imported from SQL", "Human review is a hard requirement. The live catalog needs one writer."],
        ["Attributes", "Gemini output must match the category schema", "The requirement is alignment to Cymbal’s structure, so free text is a failed job."],
        ["Images", "Imagen, private until approved, templated overlays", "Covers variants, backgrounds, color, and text without putting unreviewed pixels on the CDN."],
        ["Databases", "Cloud SQL, Memorystore, Firestore via Database Migration Service", "Same engines the teams know, managed, and a direct leave-the-data-center path."],
        ["Files", "Apigee for new partners, private SFTP adapter with an end date", "Modernizes without pretending every supplier changes this quarter."],
        ["Calls", "One Orders API, virtual agent, Agent Assist", "Removes re-keying, which is the staffing cost, and keeps people for the residue."],
        ["Journey", "Datastream CDC into BigQuery and Looker", "Breaks the silos the current estate cannot query across."],
        ["Signals", "Cloud Operations, Managed Prometheus, SLO paging", "Replaces three partial tools with one paging policy and a phased dashboard move."],
    ],
)}

<h2>14. Service map</h2>
{table(
    ["Concern", "Services"],
    [
        ["Shopper edge", "Global external Application Load Balancer, Cloud CDN, Cloud Armor, Identity Platform, GKE Autopilot"],
        ["Discovery", "AI Commerce Search, Conversational Commerce agent, Customer Engagement Suite, Agent Assist"],
        ["Enrichment", "Workflows, Eventarc, Vertex AI Gemini, Imagen, Document AI, Cloud Run review desk, IAP"],
        ["Record and speed", "Cloud SQL for MySQL, Cloud SQL for SQL Server, Memorystore, Firestore, Secret Manager"],
        ["Integration", "Apigee, Pub/Sub, Dataflow, Cloud Storage, Sensitive Data Protection"],
        ["Insight", "Datastream, BigQuery, Looker, Dataplex"],
        ["Move and run", "Database Migration Service, Cloud Build, Artifact Registry, Binary Authorization, Cloud Deploy, Infrastructure Manager"],
        ["Protect and observe", "VPC Service Controls, Cloud KMS, Cloud Logging, Cloud Monitoring, Cloud Trace, Managed Service for Prometheus"],
    ],
)}
<p class="small">This is a proposed target for design review against the published Cymbal Retail case study. It is not an official exam answer key. Engine versions, retention, and the payment-processor contract are confirmed in discovery before build.</p>
"""
    return {
        "slug": "cymbal",
        "filename": "Cymbal_Retail_Target_Architecture.pdf",
        "footer": "Cymbal Retail  ·  Target architecture  ·  PCA case study",
        "accent": ACCENT,
        "accent2": ACCENT2,
        "band": BAND,
        "cover": "linear-gradient(165deg, #3f0a18 0%, #9f1239 55%, #4c0519 100%)",
        "kicker": "Professional Cloud Architect  ·  Case study architecture",
        "title": "Cymbal Retail",
        "subtitle": "Target architecture for catalog enrichment, conversational commerce, and a smaller estate",
        "lede": "A retail record in Cloud SQL, a managed commerce index for discovery, and generative catalog work that reaches shoppers only after an associate publishes it.",
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
            ("Estate today", "Kubernetes, MySQL, SQL Server, Redis, MongoDB"),
            ("Pain today", "SFTP and batch, SQL search, re-keyed orders"),
        ],
        "cover_points": [
            "Cloud SQL remains the catalog, price, stock, and order record.",
            "AI Commerce Search answers discovery, and only from published rows.",
            "Generated copy and images reach shoppers after an associate approves them.",
            "The website, the conversational agent, and the call center share one orders API.",
        ],
        "cover_foot": "Aligned to the six pillars of the Google Cloud Well-Architected Framework. Service names as of November 2026.",
        "body": body,
    }
