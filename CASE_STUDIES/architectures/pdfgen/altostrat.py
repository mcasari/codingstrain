"""Altostrat Media target architecture."""

from render import banded, columns, figure, flow, note, table

ACCENT = "#312e81"
ACCENT2 = "#b45309"
BAND = "#f3efe6"


def document() -> dict:
    layers = banded(
        [
            (
                "People",
                [
                    ("Viewers", "Web, mobile, and living-room apps"),
                    ("Editors", "CMS, review, and appeals"),
                    ("Support", "24/7 chat, then a person"),
                    ("Ingest sites", "On-premises masters and archive"),
                ],
            ),
            (
                "Edge",
                [
                    ("Media CDN", "Signed playback of ABR ladders"),
                    ("App LB + Armor", "Global HTTPS and WAF"),
                    ("Identity Platform", "Viewers and external IdPs"),
                    ("Workforce federation", "Editors and operators"),
                ],
            ),
            (
                "Applications",
                [
                    ("GKE Autopilot", "CMS, playback, pricing, recs"),
                    ("Cloud Run", "Chat BFF and review UI"),
                    ("Conversational agent", "Grounded NLU and tools"),
                    ("Workflows", "Ingest, moderate, summarize"),
                ],
            ),
            (
                "Intelligence",
                [
                    ("Gemini on Vertex AI", "Summaries, support, copy"),
                    ("Video, Vision, Speech", "Metadata and safety signals"),
                    ("Transcoder API", "HLS and DASH ladders"),
                    ("Vector Search", "Grounding for chat and recs"),
                ],
            ),
            (
                "Data",
                [
                    ("Cloud Storage", "Dual-region media and quarantine"),
                    ("Pub/Sub + Dataflow", "Playback and decision events"),
                    ("BigQuery", "Warehouse, features, audit"),
                    ("Firestore", "Review queue and price policy"),
                ],
            ),
            (
                "Foundation",
                [
                    ("GKE Enterprise", "Cloud and on-premises fleet"),
                    ("Interconnect", "Primary path, HA VPN backup"),
                    ("Shared VPC + VPC-SC", "Private access, one perimeter"),
                    ("Build, Deploy, Ops", "Supply chain and SLOs"),
                ],
            ),
        ],
        ACCENT,
        BAND,
    )
    hybrid = columns(
        [
            (
                "Remaining on-premises",
                [
                    "Ingest workstations and archive",
                    "Attached GKE cluster",
                    "Same Config Sync policies",
                    "Identity provider already in use",
                ],
            ),
            (
                "Hybrid path",
                [
                    "Dedicated Interconnect, two links",
                    "Cloud Router and BGP",
                    "HA VPN as the backup path",
                    "Private Google Access and PSC",
                ],
            ),
            (
                "Google Cloud",
                [
                    "Landing bucket, then workflow",
                    "Quarantine until moderation",
                    "Dual-region media bucket",
                    "BigQuery and Vertex AI inside the perimeter",
                ],
            ),
        ],
        ACCENT,
        BAND,
    )
    ingest = flow(
        [
            ("Land", "Master file in the ingest bucket"),
            ("Understand", "Speech, vision, video intelligence"),
            ("Decide", "Safety scores and a rubric"),
            ("Summarize", "Gemini, grounded on the transcript"),
            ("Publish", "Transcode, sign, and CDN"),
        ],
        ACCENT,
    )
    support = flow(
        [
            ("Ask", "Signed-in viewer, natural language"),
            ("Ground", "Vector Search over titles and help"),
            ("Act", "Tools for account, search, tickets"),
            ("Record", "Prompt, model, and answer audited"),
        ],
        ACCENT,
    )
    reach = flow(
        [
            ("Events", "Plays, searches, abandons"),
            ("Features", "Dataflow into BigQuery"),
            ("Rank", "BigQuery ML plus online features"),
            ("Offer", "Price inside a policy envelope"),
        ],
        ACCENT,
    )

    body = f"""
<h2>1. Purpose</h2>
<p>This paper is the target architecture for Altostrat Media. Altostrat already runs the content platform on Google Cloud: GKE for the product, Cloud Storage for the library, BigQuery for audience analytics, and Cloud Run functions for transcode, metadata, and recommendations. Ingest and archive still sit on-premises. Identity is already a mix of Google Identity and external providers. Monitoring is Cloud Monitoring plus Prometheus, and alerts still arrive mainly as email.</p>
<p>The business ask is a governed generative-AI layer on that estate: natural-language access, 24/7 support, summaries, metadata, harmful-content controls, and revenue tools (recommendations, dynamic pricing, targeted marketing), with reliability and storage cost as the two priorities named by leadership. The architecture keeps playback independent of model availability, and it keeps every model decision reconstructable.</p>
<p class="small">Product names follow Google Cloud as of November 2026. Cloud Run functions is the current name of the serverless functions Altostrat already runs. GKE Enterprise is the fleet-management platform for clusters in Google Cloud and attached clusters outside it.</p>

<h2>2. Assumptions</h2>
{table(
    ["Topic", "Assumption used in this design"],
    [
        ["Audience geography", "The largest audience sits in one continent. Two regions in that continent are Region A (primary) and Region B (failover and dual-region storage). The pattern moves as a pair if the audience is elsewhere."],
        ["Rights", "Masters are confidential until published. Delivery objects are private. Viewers receive short-lived signed URLs or signed cookies."],
        ["Human review", "News and anything the safety models mark as ambiguous is reviewed by an editor before it is public. Low-risk library titles can auto-publish inside a written policy."],
        ["On-premises life", "Ingest and archive migrate in this program. Until the archive has moved, those sites remain a first-class part of the platform."],
        ["Models", "Gemini on Vertex AI is the default generative model. A later model change is a configuration change because prompts, schemas, and audit fields are versioned."],
    ],
)}

<h2>3. Requirements and the response</h2>
{table(
    ["ID", "Requirement", "Architecture response"],
    [
        ["BR1", "Faster, more reliable operations across cloud and on-premises", "One fleet, one pipeline, SLO burn alerts, and a hybrid path with a backup."],
        ["BR2", "Simpler infrastructure so application teams can ship", "GKE Autopilot, Cloud Run, and a paved Cloud Deploy path."],
        ["BR3", "Lower storage cost with availability and scale kept", "Dual-region media, Autoclass, lifecycle on masters, just-in-time ladders for the long tail, Media CDN."],
        ["BR4", "Natural language and 24/7 support", "Grounded conversational agent with tools, and a keyword-and-help fallback when the model is down."],
        ["BR5", "Concise summaries", "One workflow for video, audio, and documents; Gemini writes from the extracted text."],
        ["BR6", "Rich metadata from NLP and computer vision", "Speech-to-Text, Video Intelligence, Vision AI, and Natural Language, stored beside the asset."],
        ["BR7", "Detect and filter inappropriate content", "Detector stack, quarantine bucket, human queue, no CDN signature until publish."],
        ["BR8", "Trends and insights from the library and the audience", "Playback events through Pub/Sub and Dataflow into the existing BigQuery warehouse."],
        ["BR9", "Content decisions made from data", "Looker on curated BigQuery marts, including moderation outcomes and experiment results."],
        ["TR1", "Modern CI/CD and a central management platform", "Cloud Build, Artifact Registry, Binary Authorization, Cloud Deploy, Config Sync, Infrastructure Manager."],
        ["TR2", "Secure, high-performance hybrid connectivity for ingest", "Dedicated Interconnect on two links, HA VPN backup, private access only."],
        ["TR3", "Kubernetes that performs on-premises and in the cloud", "GKE Enterprise fleet: Autopilot in cloud, attached cluster on-premises until cutover."],
        ["TR4", "Storage cost control as the library grows", "Same control set as BR3, plus object versioning, soft delete, and Recommender review."],
        ["TR5", "AI detection of harmful content", "Video Intelligence explicit content, Vision SafeSearch, a fixed Gemini rubric, human review."],
        ["TR6", "Auditable, explainable AI", "Every decision stores model version, prompt version, input hash, scores, and reviewer."],
        ["TR7", "LLMs and conversational AI for personalization and reach", "Grounded recommendations and editorial share-copy. Reach is measured in BigQuery."],
        ["TR8", "Chatbots with real language understanding", "Conversational agent, retrieval over Vector Search, tool calls into the platform."],
        ["TR9", "Summaries for diverse media", "Document AI for documents, speech for audio, the video pipeline for video, one summary contract."],
    ],
    id_col=True,
)}

<h2>4. Current state</h2>
<p>The cloud estate is the right foundation and this program does not replace it. GKE already gives a horizontal scale path for the CMS and the playback API. Cloud Storage already holds the library. BigQuery is already the warehouse for behavior, consumption, and demographics. Cloud Run functions already react to object and application events.</p>
<p>Three gaps set the design. Ingest and archive are outside the fleet, so operations differ by site. Email alerts do not page anyone when an SLO is burning. Generative features, if bolted on as ungrounded calls, would sit on the playback path and would be impossible to explain to an editor or an auditor.</p>

<h2>5. Target architecture</h2>
<p>Playback, money, and models are three different planes. Viewers receive video from Media CDN. Price, entitlement, and the catalog API run on GKE Autopilot. Summaries, moderation, support, and marketing copy run as workflows and a conversational agent that call Vertex AI. A failure in the model plane delays a summary or switches chat to search. It does not stop a program that is already in the CDN cache.</p>
{figure(layers, "Figure 1. Altostrat target architecture, from viewers and ingest sites down to the platform foundation.")}
<p>Region A serves interactive traffic. Region B is the other half of the dual-region bucket and the failover target for GKE and Cloud Run. Cloud Storage dual-region keeps masters and renditions available across that pair. CDN edges cache public renditions close to viewers, so most plays never touch either region.</p>
{note("<strong>Reliability boundary.</strong> The playback API checks entitlement and returns a signed URL. It does not call Gemini, Video Intelligence, or the recommender. Personalization is a separate request the client can skip.")}

<h3>Hybrid ingest</h3>
<p>On-premises ingest and the archive stay until Storage Transfer Service has moved the archive and editors have cut over. During that period the on-premises workers run on a GKE cluster attached to the same GKE Enterprise fleet, with the same Config Sync policies and the same Binary Authorization attestors. Masters cross Dedicated Interconnect into a landing bucket. They do not cross the public internet.</p>
{figure(hybrid, "Figure 2. Ingest path from the remaining sites into the moderation and media buckets.")}
<p>Two Interconnect links in different edge availability domains, with Cloud Router and BGP, are the primary path. HA VPN is built and tested as the backup, sized for a reduced ingest rate rather than for the full archive migration. Migration windows use Storage Transfer Service with a bandwidth cap so playback and live ingest keep their share of the links.</p>

<h3>What happens to a new asset</h3>
{figure(ingest, "Figure 3. From a landed master to a signed, cached rendition.")}
<p>Eventarc starts a Workflow when a master lands. Speech-to-Text produces the transcript for audio and video. Video Intelligence and Vision AI produce shots, labels, and safety annotations. Document AI produces text for articles and scripts. Natural Language adds entities. Gemini then writes a short summary from that text, not from an open-ended prompt over the raw file alone.</p>
<p>Safety is a gate, not a label on an already public object. The master and any rendition stay in a quarantine bucket with public access prevention until the decision is publish. High-confidence violations wait for an editor. Ambiguous scores wait for an editor. Low-risk assets inside the written policy publish, and the workflow calls the Transcoder API for the delivery ladder, writes metadata to the catalog, and only then can the playback API sign a URL. Appeals append a new moderation row. They do not overwrite the original decision.</p>

<h3>Support and personalization</h3>
{figure(support, "Figure 4. A support turn is grounded, authorized, and stored.")}
<p>The conversational agent answers from Vector Search over summaries, catalog metadata, and help articles, and from tool calls: search the catalog, read the signed-in entitlement, open a ticket. The viewer’s token comes from Identity Platform. The agent never receives a password. If Gemini or Vector Search errors, the BFF on Cloud Run serves keyword search and the static help flow and tells the viewer that guided support is reduced. A person still receives the ticket.</p>
{figure(reach, "Figure 5. Recommendations and price use warehouse features and a policy envelope.")}
<p>Playback and search events already belong in BigQuery. Dataflow lands them in partitioned, clustered tables. BigQuery ML ranks candidates. Online features are served from Vertex AI Feature Store so the recs API on GKE stays fast. Gemini writes the “why this title” line from catalog metadata after the ranker has chosen the titles. Dynamic price is a second model whose output is clamped to a minimum and maximum stored in Firestore and owned by the commercial team. Moves outside that band are proposals in the same review queue editors already use. Marketing audiences are built in BigQuery and exported only where consent is present.</p>
<p>Reach, which the case study calls virality, is an editorial measurement: topic velocity in BigQuery, a drafted share line from Gemini, and an editor approval before the line is used. The platform does not auto-post.</p>

<h2>6. Platform, identity, and delivery</h2>
<h3>Resource hierarchy</h3>
<p>An organization folder holds bootstrap projects for logging, security, and the network host. Production folders separate media, data, and AI so that a deployment pipeline cannot also change a KMS key or a perimeter. Non-production mirrors them. Infrastructure Manager applies Terraform from a reviewed module set. Organization policies turn off service-account key creation, require uniform bucket-level access, and restrict public IPs on the media projects.</p>
<h3>Clusters and serverless</h3>
<p>Customer and editor APIs that need steady connections and local caching run on GKE Autopilot, private clusters, Workload Identity, and regional control planes. Chat, the review UI, and event glue run on Cloud Run with direct VPC egress. Cloud Run functions stay for small event handlers. Long media work is the Transcoder API and Video Intelligence, orchestrated by Workflows, because a function is the wrong place to own an encode ladder.</p>
<h3>Supply chain</h3>
<p>Cloud Build builds from the repo, writes provenance, and scans images in Artifact Registry. Binary Authorization admits only attested images. Cloud Deploy promotes dev, staging, and production with a canary on GKE. Config Sync and Policy Controller keep the on-premises attached cluster and the cloud clusters on the same constraints. That is the centralized management platform the technical requirements ask for.</p>
<h3>Identity</h3>
<p>Viewers stay on Identity Platform, which already fits a mix of Google Identity and third-party IdPs. Employees use Workforce Identity Federation against the existing workforce provider. Workloads use Workload Identity. There is no long-lived key in the media pipeline.</p>
<h3>Operations</h3>
<p>Managed Service for Prometheus keeps the exporters the team already runs. Cloud Logging, Cloud Monitoring, and Cloud Trace are the system of record. Alerts fire on SLO burn for playback, ingest acceptance, moderation latency, and chat, and they page through a non-email channel. Email remains a digest. Error Reporting catches application exceptions. A quarterly test fails over a non-production playback stack to Region B and restores a master from a versioned object.</p>

<h2>7. Data and storage design</h2>
{table(
    ["Store", "Contents", "Control"],
    [
        ["Landing bucket", "Masters just arrived", "Regional, retention in days, event to Workflows"],
        ["Quarantine bucket", "Unpublished bytes", "Public access prevention, no CDN, CMEK"],
        ["Media bucket", "Masters and renditions", "Dual-region, Autoclass, versioning, soft delete, CMEK"],
        ["BigQuery", "Events, features, decisions, audiences", "Partitioned tables, CMEK, column policy on identifiers, Dataplex catalog"],
        ["Firestore", "Review tasks and price bands", "Regional, IAM on the review app only"],
        ["Vector Search", "Embeddings of summaries and help", "Rebuilt from the warehouse, not a second source of truth"],
    ],
)}
<p>Autoclass places cold masters in colder classes without a hand-maintained lifecycle for every show. Delivery renditions of current titles stay hot because CDN origin misses must be cheap and fast. The long tail is transcoded on first request, then cached. Editors’ working copies are not stored in the multi-region class. Noncurrent versions expire. Soft delete covers an accidental overwrite of a master.</p>
<p>BigQuery remains the warehouse they already paid to build. New tables are <span class="small">moderation_decision</span>, <span class="small">summary_provenance</span>, <span class="small">price_experiment</span>, and the feature tables behind recommendations. Looker is the only supported path for content-strategy dashboards, so metric definitions stay in one semantic layer.</p>

<h2>8. Responsible AI</h2>
<p>This is the control that makes generative features acceptable on a media platform. Each summary, moderation outcome, chat answer that used a tool, price proposal, and marketing line stores: asset or conversation id, input hash, prompt-template version, model id and version, safety scores, raw model output, final published text, and the editor’s identity and timestamp when a person acted. The row lives in BigQuery inside the VPC Service Controls perimeter. Cloud Logging holds the operational trace. Retention follows the legal schedule, and access is limited to trust-and-safety, audit, and the on-call role.</p>
<p>Safety filters on Gemini stay on. The moderation rubric is configuration, reviewed like code. Training or tuning on viewer chats or on quarantined media requires a separate approval; it is outside this platform’s default path. Editors can see why a title was held, because the scores and the rubric version are on the review task.</p>

<h2>9. Well-Architected alignment</h2>
{table(
    ["Pillar", "How this architecture applies it"],
    [
        ["Operational excellence", "SLOs and burn alerts replace email as the paging path. Cloud Deploy, Config Sync, and Infrastructure Manager make shipping and fleet policy repeatable. Managed Prometheus meets the team on the tools they already run. Failover and restore are rehearsed."],
        ["Security, privacy, and compliance", "Private clusters, VPC Service Controls, CMEK, signed URLs, Workforce Identity Federation, and Binary Authorization. AI decisions are logged. Consent gates marketing export. Quarantine is the default for new bytes. The design follows the Secure AI Framework practice of knowing which model answered and on which input."],
        ["Reliability", "CDN and signed URLs carry playback. Region B and dual-region storage cover a regional loss. HA VPN covers an Interconnect loss at reduced ingest rate. Chat degrades to search. Moderation fails closed for new publishes and fails open only for already cached titles."],
        ["Cost optimization", "Autoclass, long-tail just-in-time transcode, CDN offload, Autopilot, and BigQuery ML before any custom training cluster. Committed use is purchased against the steady GKE and BigQuery baseline after one quarter of measurement, not on day one."],
        ["Performance optimization", "Media CDN and ABR do the viewer path. Feature Store and Memorystore, where a hot key needs it, do recommendations. Gemini is asynchronous except for an interactive chat turn, which has its own latency budget and a fallback."],
        ["Sustainability", "CDN and just-in-time transcode avoid encoding and storing ladders nobody watches. Autopilot and Cloud Run scale down. Batch enrichment runs in the region already chosen for the audience, so data does not move continent for a batch window. Idle GPU notebooks are not part of this platform."],
    ],
)}

<h2>10. Service levels and disaster recovery</h2>
{table(
    ["Journey", "SLI", "Target", "When a dependency fails"],
    [
        ["Playback start", "Share of sessions that receive a playable URL and first bytes", "99.9% monthly", "CDN and Region B. Models are not involved."],
        ["Ingest accept", "Masters acknowledged into the landing bucket", "99.9% monthly", "HA VPN at a lower bitrate. Queue on site if both paths fail."],
        ["Publish of a clean asset", "Time from land to publishable rendition", "95% under 30 minutes for a standard hour", "Queue and retry. Editors see the state."],
        ["Chat", "Successful grounded answer or intentional fallback", "99.5% monthly", "Keyword search and ticket. No invented policy answers."],
        ["Review queue", "Tasks visible to editors", "99.9% monthly", "Firestore regional. Publishing stays closed if the queue cannot be written."],
    ],
)}
<p>Recovery point for masters is near zero once the object is in the dual-region bucket with versioning. Recovery time for the playback API is the time to shift the load balancer to Region B, targeted under one hour, which sits comfortably inside a 99.9% error budget if regional failover is rare. BigQuery time travel covers analytical mistakes. A deleted master is restored from a noncurrent version or soft delete. Disaster recovery for the on-premises archive is still the on-premises procedure until that archive has moved.</p>

<h2>11. Security and privacy</h2>
<p>The media, data, and AI projects sit in one VPC Service Controls perimeter. Cloud Storage, BigQuery, and Vertex AI are accessed through Private Service Connect. Editors reach the review UI through Identity-Aware Proxy. Cloud Armor sits on the public application load balancer with a WAF policy and rate limits. CMEK from Cloud KMS wraps media buckets, BigQuery, and the Vector Search index. Secrets and the price-band administration credentials live in Secret Manager.</p>
<p>Viewer identifiers used for recommendations are separated from marketing exports. Targeted marketing reads an audience that BigQuery column policies and a consent flag already restrict. Support transcripts are in the same audit dataset as model provenance, with a retention limit and no copy into the editorial warehouse.</p>

<h2>12. Cost and sustainability</h2>
<p>The expensive bytes are masters and unused ladders, not the Kubernetes control plane. The design spends on dual-region storage and CDN for what viewers actually play, and it spends on managed media APIs per minute processed. It avoids a standing GPU fleet. Autoclass and long-tail transcode are the two controls leadership can see on the bill. A monthly review uses Cloud Billing export to BigQuery and Recommender, and it checks that quarantine buckets are not accumulating unpublished renditions.</p>

<h2>13. Roadmap</h2>
{table(
    ["Phase", "Outcome"],
    [
        ["1. Foundation", "Folders, Shared VPC, perimeter, CMEK, Interconnect and HA VPN, fleet, Cloud Deploy, SLO paging, landing and quarantine buckets."],
        ["2. Media path", "Workflow from land to Transcoder to signed CDN. Existing Cloud Run functions retire off the encode path as each format is covered."],
        ["3. Trust", "Safety gate, review UI, provenance tables, summary workflow for video, audio, and documents."],
        ["4. Audience", "Grounded chat with fallback, BigQuery ML recommendations, price envelope, Looker content marts, consent-bound audiences."],
        ["5. Exit sites", "Storage Transfer Service empties the archive. Ingest cutover. Attached cluster decommissioned when the last site closes."],
    ],
)}

<h2>14. Decision record</h2>
{table(
    ["Topic", "Choice", "Why this is the fit"],
    [
        ["Kubernetes", "GKE Enterprise fleet, Autopilot in cloud, attached cluster on-premises", "One management plane for both places the case study requires, with less node toil in cloud."],
        ["Ingest network", "Dedicated Interconnect and HA VPN", "Master files need a private, high-throughput path and a tested second path."],
        ["Bytes", "Dual-region bucket, Autoclass, Media CDN, just-in-time long tail", "Availability for the library without keeping every cold master in the hottest class."],
        ["Encode", "Transcoder API orchestrated by Workflows", "ABR ladders are a managed media problem. Functions remain for short events."],
        ["Language", "Gemini grounded on extracted text and Vector Search", "Summaries and answers have a source the editor can open."],
        ["Safety", "Detectors, quarantine, human decisions, append-only audit", "Filtering is a publish gate with a reason, which is what explainability means here."],
        ["Rank and price", "BigQuery ML, Feature Store, a commercial policy band", "Uses the warehouse they have. The model cannot set a price the business did not authorize."],
        ["Delivery of changes", "Cloud Build, Artifact Registry, Binary Authorization, Cloud Deploy", "The centralized, attestable path the CI/CD requirement describes."],
        ["Paging", "SLO burn to an incident channel", "Email remains available and stops being the only signal, which is how current alerts are missed."],
    ],
)}

<h2>15. Service map</h2>
{table(
    ["Concern", "Services"],
    [
        ["Edge and security", "Media CDN, Global external Application Load Balancer, Cloud Armor, Identity Platform, Workforce Identity Federation, IAP, Cloud KMS, VPC Service Controls, Secret Manager"],
        ["Compute", "GKE Autopilot, GKE Enterprise, Cloud Run, Cloud Run functions, Workflows, Eventarc"],
        ["Media and AI", "Transcoder API, Video Intelligence, Vision AI, Speech-to-Text, Natural Language, Document AI, Vertex AI Gemini, Vector Search, Feature Store"],
        ["Data", "Cloud Storage, Pub/Sub, Dataflow, BigQuery, BigQuery ML, Firestore, Looker, Dataplex, Storage Transfer Service"],
        ["Delivery and ops", "Cloud Build, Artifact Registry, Binary Authorization, Cloud Deploy, Config Sync, Policy Controller, Infrastructure Manager, Cloud Monitoring, Cloud Logging, Cloud Trace, Managed Service for Prometheus"],
        ["Network", "Shared VPC, Cloud Router, Dedicated Interconnect, HA VPN, Private Service Connect, Private Google Access"],
    ],
)}
<p class="small">This is a proposed target for design review against the published Altostrat Media case study. It is not an official exam answer key. Sizing, regions, and retention periods are confirmed in discovery before build.</p>
"""
    return {
        "slug": "altostrat",
        "filename": "Altostrat_Media_Target_Architecture.pdf",
        "footer": "Altostrat Media  ·  Target architecture  ·  PCA case study",
        "accent": ACCENT,
        "accent2": ACCENT2,
        "band": BAND,
        "cover": "linear-gradient(165deg, #16132e 0%, #312e81 58%, #1f1b4d 100%)",
        "kicker": "Professional Cloud Architect  ·  Case study architecture",
        "title": "Altostrat Media",
        "subtitle": "Target architecture for a governed media platform and generative AI",
        "lede": "A dual-region media plane on GKE Enterprise, Media CDN, and Cloud Storage, with Vertex AI summaries, moderation, support, and revenue tools that never sit on the playback path.",
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
            ("Estate today", "GKE, Cloud Storage, BigQuery, Cloud Run functions"),
            ("Still outside", "On-premises ingest and archive"),
        ],
        "cover_points": [
            "Playback is served from Media CDN and does not call a model.",
            "New media stays in quarantine until a safety decision is recorded.",
            "Ingest crosses Dedicated Interconnect, with HA VPN as the backup path.",
            "Summaries, chat, price, and recommendations are grounded and auditable.",
        ],
        "cover_foot": "Aligned to the six pillars of the Google Cloud Well-Architected Framework. Service names as of November 2026.",
        "body": body,
    }
